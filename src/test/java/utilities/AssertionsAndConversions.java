package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Employee;

public class AssertionsAndConversions {

	private static final ObjectMapper Mapper = new ObjectMapper();

	public static Employee convertJsonResponse(String responseJsonString) {
		try {
			Mapper.setSerializationInclusion(Include.NON_NULL);
			Mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			return Mapper.readValue(responseJsonString, Employee.class); // if expecting a list, then give [] of the
																			// class and then convert to arrays.aslist
		} catch (final Exception e) {
			throw new AssertionError("Assertion Failed for convertJsonResponse : " + e);
		}
	}

	public static String readJSONAsString(String path) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(path)) {
			try {
				String sCurrentLine = null;
				br = new BufferedReader(new FileReader(path));
				while ((sCurrentLine = br.readLine()) != null) {
					sb.append(sCurrentLine);
				}
			} catch (IOException e) {
				throw new AssertionError("Assertion Failed for readJSONAsString : " + e);
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return sb.toString();
	};

	public static void assertEmployee(Employee expected, Employee recieved) {
		Assert.assertEquals(expected.getCompany(), recieved.getCompany());
		Assert.assertEquals(expected.getDeptId(), recieved.getDeptId());
		Assert.assertEquals(expected.getDeptName(), recieved.getDeptName());		
		Assert.assertEquals(expected.getEmployeeId(), recieved.getEmployeeId());
		Assert.assertEquals(expected.getEmployeeName(), recieved.getEmployeeName());
	}

}
