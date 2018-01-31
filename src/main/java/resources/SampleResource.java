package resources;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Employee;

@Path("/hello")
@Api
public class SampleResource {

	@GET
	@Path("/{id}")
	@ApiOperation(value = "Retrieve a employee record")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee sayHtmlHello() {
		return retrieveEmployee();
	}
	
	private Employee retrieveEmployee() {
		Employee emp=new Employee();
		emp.setCompany("Freddie");
		emp.setDeptId("S01");
		emp.setDeptName("sales");
		emp.setEmployeeId("f123");
		emp.setEmployeeName("Pact");
		return emp;
		
	}

}
