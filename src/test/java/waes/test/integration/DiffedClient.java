package waes.test.integration;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import io.restassured.response.Response;

@Ignore
public class DiffedClient {

	@Test 
	public void saveLeft(){
		
		Response response = given()
                .get("http://localhost:8080/v1/diff/1");
       // response.print();
        
        String status = response.jsonPath().getString("status");
        assertEquals("DIFF", status);
	}
	
}
