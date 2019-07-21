package teluscucumber;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountPATCHSteps {
	String requestBody;
	AccountGETSteps newGETCall = new AccountGETSteps();
	
	@Given("User provides a set of attributes with values")
	public void user_provides_a_set_of_attributes_with_values(String docString) {
	    assertNotNull(docString);
	    assertFalse(docString.trim().equals(""));
		requestBody = docString;
	}

	@When("User calls PATCH method of account webservice")
	public void user_calls_PATCH_method_of_account_webservice() {
		TestUtils.webServiceResponse = 
				given()
					.request().body(this.requestBody)
				.when()
					.patch(TestUtils.BASE_ACCOUNT_URI+"/"+ TestUtils.accountId);
	}

	@Then("A GET webservice for patched account id {int} returns status code {int}")
	public void a_GET_webservice_for_patched_account_id_returns_status_code(Integer patchedAccId, Integer statusCode) {
		newGETCall.user_passes_account_id(patchedAccId);
		newGETCall.user_wants_to_GET_account_information();
		newGETCall.validate_response_status_code_is(statusCode);
	}

	@Then("The GET webservice for patched account id shows new balance {double}")
	public void the_GET_webservice_for_patched_account_id_shows_new_balance(Double newBalance) {
		newGETCall.validate_response_s_attribute_balance_is(newBalance);
	}

	@Then("The GET webservice for patched account id shows new status {string}")
	public void the_GET_webservice_for_patched_account_id_shows_new_status(String newStatus) {
		newGETCall.validate_response_s_account_s_status_is(newStatus);
	}
}
