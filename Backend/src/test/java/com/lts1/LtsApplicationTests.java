package com.lts1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.lts1.model.Leaves;
import com.lts1.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LtsApplicationTests {

	RestTemplate restTemplate = new RestTemplate();
	
	@LocalServerPort
	int port;
	
	final private String baseUrl = "http://localhost:";
	
	//Register and Login Tests

	@Test
	@Disabled
	@DisplayName("Should register on valid details")
	public void shouldRegisterOnValidDetails() throws URISyntaxException{
        URI uri = new URI(baseUrl+port +"/register");
		User user = new User("charan", "9494070203", "1charan@edtex.in", "charan", "employee", 10,10);
		ResponseEntity<Object> response= restTemplate.postForEntity(uri, user, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String statusCode = documentContext.read("$.statusCode");
		assertEquals("CREATED", statusCode);
	}
	
	@Test
	@DisplayName("Should not register on invalid details")
	public void shouldNotRegisterOnInvalidDetails() throws URISyntaxException {
		URI uri = new URI(baseUrl+port +"/register");
		User user = new User("charan", "9494070203", "charan@edtex.in", "charan", "employee", 10,10);
		ResponseEntity<Object> response= restTemplate.postForEntity(uri, user, Object.class);
		assertEquals(HttpStatus.OK,response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String statusCode = documentContext.read("$.statusCode");
		assertEquals("BAD_REQUEST", statusCode);
	}
	@Test
	@DisplayName("Should login on giving Valid Credentials")
	public void shouldLoginOnValidCredentials() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/login");
		User user = new User();
		user.setEmail("charan@edtex.in");
		user.setPassword("charan");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri, user, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String statusCode = documentContext.read("$.statusCode");
		assertEquals("OK", statusCode);
	}
	@Test
	@DisplayName("Should not login on giving non existing email")
	public void shouldNotLoginOnGivingNonExistingEmail() throws URISyntaxException {
		URI uri = new URI(baseUrl + port + "/login");
		User user = new User();
		user.setEmail("email");
		user.setPassword("password");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri, user, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String statusCode = documentContext.read("$.statusCode");
		assertEquals("NOT_FOUND", statusCode);
	}
	
	@Test
	@DisplayName("Should not login on giving wrong password")
	public void shouldNotLoginOnGivingWrongPassword() throws URISyntaxException {
		URI uri = new URI(baseUrl + port + "/login");
		User user = new User();
		user.setEmail("charan@edtex.in");
		user.setPassword("password");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri, user, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String statusCode = documentContext.read("$.statusCode");
		assertEquals("NO_CONTENT", statusCode);
	}
	
	//EmployeeController Tests
	
	@Test
	@Disabled
	@DisplayName("Should Apply leave")
	public void shouldApplyLeave() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/employee/apply-leave");
		Leaves leave = new Leaves("name","email","leave-type","start","end","reason","status","message");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri, leave,Object.class);
		assertEquals(HttpStatus.OK,response.getStatusCode());

	}
	
	@Test
	@DisplayName("Should get leaves based on User email")
	public void shouldGetLeavesBasedOnUserEmail() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/employee/get-leaves/charan@edtex.in");
		ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	@Test
	@Disabled
	@DisplayName("Should delete leave based on id")
	public void shouldDeleteLeaveBasedOnId() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/employee/delete-leave/402");
		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, Object.class);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	@Test
	@DisplayName("Should return not found if there is no leave of given id")
	public void shouldNotDeleteIfLeaveNotFound() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/employee/delete-leave/0");
		ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, Object.class);
		assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
	}
	
	@Test
	@Disabled
	@DisplayName("Should edit the existing leave based on id and leave data")
	public void shouldEditLeave() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/employee/edit-leave/505");
		Leaves leave = new Leaves("name","email","leave-type","start","end","reason","status","message");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri, leave, Object.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());	
	}
	
	
	@Test
	@DisplayName("Should get leave based on id")
	public void shouldGetLeave() throws URISyntaxException{
		URI uri = new URI ( baseUrl + port + "/employee/get-leave/402");
		ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}


	@Test
	@DisplayName("Should get leave history")
	public void shouldGetLeaveHistory() throws URISyntaxException{
		URI uri = new URI( baseUrl + port + "/employee/get-leave-history/nivas@edtex.in");
		ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	//ManagerController Tests
	
	@Test
	@DisplayName("Should retrieve new requests data")
	public void shouldRetrieveNewRequestsData() throws URISyntaxException{
		URI uri = new URI ( baseUrl + port + "/manager/new-requests");
		ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Should get overview leaves data")
	public void shouldGetOverviewLeavesData() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/manager/overview");
		ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("should react to leaves accept/reject")
	public void shoudlReactToLeaves() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/manager/react-to-leave/902/accepted");
		HttpEntity<String> request = new HttpEntity<String>("Accepted by manager");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri,"accepted by manager",Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Updating Leave Count")
	public void shouldUpdateLeaveCount() throws URISyntaxException{
		URI uri = new URI(baseUrl + port + "/manager/update-leave-count/20");
		ResponseEntity<Object> response = restTemplate.postForEntity(uri, null, Object.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
