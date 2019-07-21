package teluscucumber;

import static io.restassured.RestAssured.delete;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountDELETESteps {
	AccountGETSteps newGETCall = new AccountGETSteps();
	
	@When("User calls DELETE method of account webservice")
	public void user_calls_DELETE_method_of_account_webservice() {
		TestUtils.webServiceResponse = 
				delete(TestUtils.BASE_ACCOUNT_URI+"/"+ TestUtils.accountId);
	}

	@Then("A GET call with deleted account id {int} returns status code {int}")
	public void a_GET_call_with_deleted_account_id_returns_status_code(Integer deletedAccountId, Integer statusCode) {
		newGETCall.user_passes_account_id(deletedAccountId);
		newGETCall.user_wants_to_GET_account_information();
		newGETCall.validate_response_status_code_is(statusCode);
	}
}
