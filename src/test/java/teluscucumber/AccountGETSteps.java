package teluscucumber;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountGETSteps {
	
	@Given("User passes a valid account id {int}")
	public void user_passes_account_id(Integer accID) {
	    assertNotNull(accID);
		TestUtils.accountId = accID.intValue();
	}

	@When("User wants to GET account information")
	public void user_wants_to_GET_account_information() {
		TestUtils.webServiceResponse = get(TestUtils.BASE_ACCOUNT_URI + "?Stepdefs.accountId=" + TestUtils.accountId);
	}
	
	@Then("Validate response status code is {int}")
	public void validate_response_status_code_is(Integer statusCode) {
		TestUtils.webServiceResponse
		.then()
		.statusCode(statusCode);
	}

	@Then("Validate response's attribute account id is {int}")
	public void validate_response_s_account_id_is(Integer accID) {
		TestUtils.webServiceResponse
		.then()
		.body("id", equalTo(accID));
	}

	@Then("Validate response's attribute status is {string}")
	public void validate_response_s_account_s_status_is(String accountStatus) {
		TestUtils.webServiceResponse
		.then()
		.body("status", equalTo(accountStatus));
	}
	
	@Then("Validate response's attribute SIN is {int}")
	public void validate_response_s_attribute_SIN_is(Integer sin) {
		TestUtils.webServiceResponse
		.then()
		.body("sin", equalTo(sin.intValue()));
	}

	@Then("Validate response's attribute balance is {double}")
	public void validate_response_s_attribute_balance_is(Double balance) {
		TestUtils.webServiceResponse
		.then()
		.body("balance", equalTo(balance.floatValue()));
	}
	
	@Then("Validate response has a billing address object")
	public void validate_billing_address_contains_object() {
		TestUtils.webServiceResponse
		.then()
		.body("$", hasKey("billingAddress"));
	}

	@Then("Validate address attribute {string} is not empty")
	public void validate_address_attribute_is_not_empty(String attributeName) {
		TestUtils.webServiceResponse
		.then()
		.body("billingAddress", hasKey(attributeName))
		.and()
		.body("billingAddress." + attributeName, not(equalTo(null))); 
	}
}
