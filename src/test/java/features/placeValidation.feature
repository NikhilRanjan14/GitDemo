Feature: Validating Place API

@addPlace
Scenario Outline: Verify if place is being successfully added using addPlace API
	Given addPlace payload with "<name>" "<address>" "<language>"
	When user calls "addPlaceAPI"  using "post" request
	Then API call is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_id created maps to "<name>" using "getPlaceAPI"

Examples:
	|name   |address						 |language|
	|Nikhil |Swadesh Tower,Patna |Hindi		|	
	|BBHouse|WTC, Delhi					 |English |

@deletePlace
Scenario: Verify if deletePlace API is working successfully
	Given deletePlace payload
	When user calls "deletePlaceAPI"  using "post" request 
	Then API call is success with status code 200
	And "status" in response body is "OK"


