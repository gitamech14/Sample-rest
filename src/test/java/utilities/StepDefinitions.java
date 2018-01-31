package utilities;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import model.Employee;

public class StepDefinitions {

	CucumberBean bean = new CucumberBean();

	@Given("^scenario \"([^\"]*)\"$")
	public void scenario(String scenario) throws Throwable {
		bean.setScenario(scenario);
	}

	@When("^the id of employee is \"([^\"]*)\"$")
	public void the_id_of_employee_is(String id) throws Throwable {
		bean.setId(id);
	}

	@Then("^the status code of \"([^\"]*)\" and \"([^\"]*)\" are returned$")
	public void the_status_code_of_and_are_returned(int expectedStatus, String expectedJson) throws Throwable {
		bean.setExpectedJson(expectedJson);
		bean.setExpectedStatus(expectedStatus);
		
		HttpClient client=HttpClients.createDefault();
		String url="http://localhost:8080/sample-rest/rest/hello/{id}";
		UriBuilder builder=UriBuilder.fromUri(url).resolveTemplate("id", bean.getId());
		HttpGet request=new HttpGet(builder.build());
		request.setHeader("Accept","application/json");
		HttpResponse response=client.execute(request);
		StatusLine statusline=response.getStatusLine();
		bean.setResponseStatus(statusline.getStatusCode());
		Assert.assertEquals(bean.getExpectedStatus(), bean.getResponseStatus());
		
		String expectedString =AssertionsAndConversions.readJSONAsString(bean.getExpectedJson());
		Employee expectedEmployee=AssertionsAndConversions.convertJsonResponse(expectedString);
		
		bean.setResponseJson(IOUtils.toString(response.getEntity().getContent()));
		Employee respEmployee=AssertionsAndConversions.convertJsonResponse(bean.getResponseJson());
				
		AssertionsAndConversions.assertEmployee(expectedEmployee, respEmployee);
		
		
	}

}
