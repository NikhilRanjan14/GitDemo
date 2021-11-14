package stepDefinition;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoAddPlace.AddPlace;
import pojoAddPlace.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class PlaceValidationSteps extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	
	
	@Given("addPlace payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String address, String language) throws IOException {	
		res = given().spec(requestSpecification())
				.body(data.addPlacePayload(name,address,language));
		System.out.println("add_place_payload");
	    
	}
	
	@When("user calls {string}  using {string} request")
	public void user_calls_using_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
	  
	
		// constructor will be called with value of resource you pass in the feature file
		APIResources resourceAPI = APIResources.valueOf(resource);//.valueOf uses the constructor to get the String location of the resource API called
		
	if(method.equalsIgnoreCase("POST")) {	
	response = res.when().post(resourceAPI.getResource())
				.then().spec(responseSpecification()).extract().response();
	}
	else if(method.equalsIgnoreCase("GET")) 
		response = res.when().get(resourceAPI.getResource());
	
	System.out.println("user_calls_using_request");    
	}
	 
	@Then("API call is success with status code {int}")
	public void api_call_is_success_with_status_code(int statusCode) {
		assertEquals(response.getStatusCode(),statusCode);
		System.out.println("api_call_is_success_with_status_code");
				
	    
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	
	 assertEquals(getJsonPath(response,keyValue),expectedValue);
	 System.out.println("in_response_body_is");
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		
		place_id=getJsonPath(response,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		 user_calls_using_request(resource,"GET");
		 String actualName = getJsonPath(response,"name");
		 assertEquals(expectedName,actualName);
		 System.out.println("verify_place_id_created_maps_to_using");
	
	}
	
	@Given("deletePlace payload")
	public void delete_place_payload() throws IOException {
	   res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	   System.out.println("deletePlace payload");
	}



}
