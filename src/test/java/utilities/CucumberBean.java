package utilities;

import org.apache.commons.lang3.StringUtils;

public class CucumberBean {
	
	private String id;
	
	private int expectedStatus;
	
	private int responseStatus;
	
	private String expectedJson;
	
	private String responseJson;
	
	private String scenario;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the expectedStatus
	 */
	public int getExpectedStatus() {
		return expectedStatus;
	}

	/**
	 * @param expectedStatus the expectedStatus to set
	 */
	public void setExpectedStatus(int expectedStatus) {
		this.expectedStatus = expectedStatus;
	}

	/**
	 * @return the responseStatus
	 */
	public int getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @param responseStatus the responseStatus to set
	 */
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * @return the expectedJson
	 */
	public String getExpectedJson() {
		return expectedJson;
	}

	/**
	 * @param expectedJson the expectedJson to set
	 */
	public void setExpectedJson(String expectedJson) {
		this.expectedJson = StringUtils.isNotBlank(expectedJson)?"src/test/java/json/"+expectedJson:expectedJson;
	}

	/**
	 * @return the responseJson
	 */
	public String getResponseJson() {
		return responseJson;
	}

	/**
	 * @param responseJson the responseJson to set
	 */
	public void setResponseJson(String responseJson) {
		this.responseJson = responseJson;
	}

	/**
	 * @return the scenario
	 */
	public String getScenario() {
		return scenario;
	}

	/**
	 * @param scenario the scenario to set
	 */
	public void setScenario(String scenario) {
		this.scenario = scenario;
	}
	
	

	
	
}
