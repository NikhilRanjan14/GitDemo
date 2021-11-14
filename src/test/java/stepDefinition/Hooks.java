package stepDefinition;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@deletePlace")
	public void beforeScenario() throws IOException {
		PlaceValidationSteps pvs = new PlaceValidationSteps();
		if(pvs.place_id==null) {
		pvs.add_place_payload_with("Nikhil", "Patna", "English");
		pvs.user_calls_using_request("addPlaceAPI", "POST");
		pvs.verify_place_id_created_maps_to_using("Nikhil", "getPlaceAPI");
	}
	}

}
