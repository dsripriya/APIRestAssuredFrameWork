package api.test;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payloads.User;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
//	public Logger logger;  //for logs
	
	@BeforeClass
	public void setUp() {
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());		//cannot be updated
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
//		logger=LogManager.getLogger(this.getClass());
//		logger.debug("***debugging***********");
		
	}
	
	@Test(priority=1)
	public void testPostUser() {
//		logger.info("********Create USEr************");
		Response response=UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
//		logger.info("********USEr created************");

	}
	
	@Test(priority=2)
	public void testGetUserByName() {
//		logger.info("********Reading USEr************");

		Response response=UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
//		logger.info("********Read USEr displayed************");

	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		//Update same data using payLoad
//		logger.info("********Update USEr************");

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		Response response=UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body().statusCode(200);	//chai assertion
		Assert.assertEquals(response.getStatusCode(), 200);		//TestNG assertion
		
		//Checking data after updation
		Response responseAfterUpdate=UserEndPoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
//		logger.info("********Updated USEr************");

	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
//		logger.info("********Delete USEr************");

		Response response=UserEndPoints2.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);	
//		logger.info("********Deleted USEr************");

	}
}
