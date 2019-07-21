Feature: Testing account REST API
  Users should be able to submit GET, POST, PATCH and DELETE requests to the account web service

  Scenario Outline: Valid Data retrieval from GET method for existing account id
    Given User passes a valid account id <accountId>
    When User wants to GET account information
    Then Validate response status code is 200
    And Validate response's attribute account id is <accountId>
    And Validate response's attribute status is "<status>"
    And Validate response's attribute SIN is <sin>
    And Validate response's attribute balance is <balance>
    And Validate response has a billing address object
  Examples:
  	| accountId | status   | sin 	   | balance |
  	| 4 		| Active   | 123456789 | 1235.05 |
#  	| 5			| Active   | 854756215 | 2281.25 | #Added for illustration only. Multiple records can be tested in this manner once web-service is ready.
  	
  Scenario Outline: Required attributes of Billing address must be not null
  	Given User passes a valid account id 4
  	When User wants to GET account information
  	And Validate address attribute "<attributeName>" is not empty
  Examples:
  	| attributeName |
  	| addressId		|
	| addressLine1  |
	| city          |
	| province	    |
	| postalCode    |
    
  Scenario: Creation of new account by calling POST method
  	Given User provides a valid account object
  		"""
	  		{
			  "value": {
			    "sin": 854756215,
			    "balance": 2281.25,
			    "status": "Active",
			    "billingAddress": {
			      "addressLine1": "321 Alaxander Street",
			      "addressLine2": "Apt 21",
			      "city": "Ottawa",
			      "province": "Ontario",
			      "postalCode": "5K1 0L9"
			    }
			  }
			}
		"""
    When User Calls the POST method of account webservice
    Then Validate response status code is 201
    And Validate that an attribute id is returned with value not null or 0
    And A GET webservice for new account id returns status code 200
  	And The GET webservice for new account id shows sin 854756215
  	And The GET webservice for new account id shows balance 2281.25
  	And The GET webservice for new account id shows status "Active"
  	And The GET webservice for new account id shows billingAddress-addressLine1 as "321 Alaxander Street"
    
  Scenario: Modify an existing account by calling PATCH method
  	Given User passes a valid account id 4
  	And User provides a set of attributes with values
  		"""
			{
			  "value": {
			    "balance": 1800.50,
			    "status": "Inactive"
			   }
			}
  		"""
  	When User calls PATCH method of account webservice
  	Then Validate response status code is 200
  	And A GET webservice for patched account id 4 returns status code 200
  	And The GET webservice for patched account id shows new balance 1800.50
  	And The GET webservice for patched account id shows new status "Inactive"
  	
  Scenario: Delete an existing account by calling DELETE method
  	Given User passes a valid account id 4
  	When User calls DELETE method of account webservice
  	Then Validate response status code is 204
  	And A GET call with deleted account id 4 returns status code 400