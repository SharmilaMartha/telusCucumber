package teluscucumber;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountPOSTSteps {
	String accountRequestJson;
	AccountGETSteps newGETCall = new AccountGETSteps();
	
	@Given("User provides a valid account object")
	public void a_valid_account_object(String docString) {
		assertNotNull(docString);
		assertFalse(docString.trim().equals(""));
		accountRequestJson = docString;
	}

	@When("User Calls the POST method of account webservice")
	public void user_Calls_the_POST_method_of_account_webservice() {
		TestUtils.webServiceResponse = 
				given()
					.request().body(accountRequestJson)
				.when()
					.post(TestUtils.BASE_ACCOUNT_URI);
	}

	@Then("Validate that an attribute id is returned with value not null or 0")
	public void validate_that_the_response_attribute_id_is_not_null_or_0() {
		TestUtils.webServiceResponse
		.then()
		.body("id", not(equalTo(null)))
		.and()
		.body("id", not(equalTo(0)));
		
		TestUtils.accountId = 
				TestUtils.webServiceResponse
				.then()
				.extract()
				.path("id");
	}
	
	@Then("A GET webservice for new account id returns status code {int}")
	public void a_GET_webservice_for_new_account_id_returns_status_code(Integer statusCode) {
		newGETCall.user_passes_account_id(TestUtils.accountId);
		newGETCall.user_wants_to_GET_account_information();
		newGETCall.validate_response_status_code_is(statusCode);
	}

	@Then("The GET webservice for new account id shows sin {int}")
	public void the_GET_webservice_for_new_account_id_shows_sin(Integer sin) {
		newGETCall.validate_response_s_attribute_SIN_is(sin);
	}

	@Then("The GET webservice for new account id shows balance {double}")
	public void the_GET_webservice_for_new_account_id_shows_balance(Double balance) {
		newGETCall.validate_response_s_attribute_balance_is(balance);
	}

	@Then("The GET webservice for new account id shows status {string}")
	public void the_GET_webservice_for_new_account_id_shows_status(String statusAttribute) {
		newGETCall.validate_response_s_account_s_status_is(statusAttribute);
	}

	@Then("The GET webservice for new account id shows billingAddress-addressLine1 as {string}")
	public void the_GET_webservice_for_new_account_id_shows_billingAddress_addressLine_as(String addressLine1) {
		TestUtils.webServiceResponse
		.then()
		.body("addressLine1", equalTo(addressLine1));
	}
}
