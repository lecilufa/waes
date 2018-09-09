package waes.test.integration;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import waes.task.model.Text;
import waes.task.util.Base64JsonUtil;


public class DiffedClient {
	
	@Test
	public void saveLeft() throws Exception {
		
		Long id = 11L;
		
		// text create
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789123456";
		text.setLeftText(leftText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/left");
		
		response.print();
		
		JsonPath json = response.jsonPath();
		Long actualId = json.getLong("id");
		Integer statusCode = response.getStatusCode();
				
		assertEquals(leftText, json.getString("leftText"));
		assertEquals(id, actualId);
		assertEquals(new Integer( HttpStatus.CREATED.value()), statusCode);
		
		// text update
		text = new Text();
		text.setId(id);
		leftText = "012345678912**56";
		text.setLeftText(leftText);
		
		encodeString = Base64JsonUtil.encodeToString(text);		
		
		response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/left");
		

		json = response.jsonPath();
		actualId = json.getLong("id");
		statusCode = response.getStatusCode();
				
		assertEquals(leftText, json.getString("leftText"));
		assertEquals(id, actualId);
		assertEquals(new Integer( HttpStatus.CREATED.value()), statusCode);
	}
	

	@Test
	public void saveRight() throws Exception {
		
		Long id = 12L;
		
		// text create
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789123456";
		text.setRightText(rightText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/right");
		
		response.print();
		
		JsonPath json = response.jsonPath();
		Long actualId = json.getLong("id");
		Integer statusCode = response.getStatusCode();
				
		assertEquals(rightText, json.getString("rightText"));
		assertEquals(id, actualId);
		assertEquals(new Integer( HttpStatus.CREATED.value()), statusCode);
		
		// text update
		text = new Text();
		text.setId(id);
		rightText = "0123ww6789123456";
		text.setRightText(rightText);
		
		encodeString = Base64JsonUtil.encodeToString(text);		
		
		response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/right");
		

		json = response.jsonPath();
		actualId = json.getLong("id");
		statusCode = response.getStatusCode();
				
		assertEquals(rightText, json.getString("rightText"));
		assertEquals(id, actualId);
		assertEquals(new Integer( HttpStatus.CREATED.value()), statusCode);
	}
	
	@Test
	public void diffed() throws Exception {
		
		Long id = 13L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789abcdefg";
		text.setLeftText(leftText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/left");
		
		
		text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg";
		text.setRightText(rightText);
		
		encodeString = Base64JsonUtil.encodeToString(text);		
		response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/right");
		
		
		response = given()
				.get("http://localhost:8080/v1/diff/"+ id);
		
		JsonPath json = response.jsonPath();
		
		Long resultId = json.getLong("id");
		String status = json.getString("status");
		List<Map<String,Object>> diffs = json.getList("diffs");
		Integer statusCode = response.getStatusCode();
		
		assertEquals(new Integer( HttpStatus.OK.value()), statusCode);
		assertEquals(id, resultId);
		assertEquals("DIFF",status);
		assertTrue(diffs.size() > 0);
				
		Integer offset = (Integer) diffs.get(0).get("offset");
		Integer length = (Integer) diffs.get(0).get("length");
		
		assertEquals(new Integer(4),offset);
		assertEquals(new Integer(2),length);
	}
	
	@Test
	public void equal() throws Exception {
		Long id = 13L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "123456";
		text.setLeftText(leftText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/left");
		
		text = new Text();
		text.setId(id);
		String rightText = "123456";
		text.setRightText(rightText);
		
		encodeString = Base64JsonUtil.encodeToString(text);		
		response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/right");
		
		response = given()
				.get("http://localhost:8080/v1/diff/"+ id);
		
		JsonPath json = response.jsonPath();
		
		Long resultId = json.getLong("id");
		String status = json.getString("status");
		List<Map<String,Object>> diffs = json.getList("diffs");
		Integer statusCode = response.getStatusCode();
		
		assertEquals(new Integer( HttpStatus.OK.value()), statusCode);
		assertEquals(id, resultId);
		assertEquals("EQUAL",status);
		assertTrue(diffs.size() == 0);
				
	}
	
	@Test
	public void notSameSize() throws Exception {
		Long id = 14L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123456789abcdefg";
		text.setLeftText(leftText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/left");
		
		text = new Text();
		text.setId(id);
		String rightText = "0123456789abcdefg***";
		text.setRightText(rightText);
		
		encodeString = Base64JsonUtil.encodeToString(text);		
		response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/right");
		
		response = given()
				.get("http://localhost:8080/v1/diff/"+ id);
		
		JsonPath json = response.jsonPath();
		
		Long resultId = json.getLong("id");
		String status = json.getString("status");
		List<Map<String,Object>> diffs = json.getList("diffs");
		Integer statusCode = response.getStatusCode();
		
		assertEquals(new Integer( HttpStatus.OK.value()), statusCode);
		assertEquals(id, resultId);
		assertEquals("NOT_SAME_SIZE",status);
		assertTrue(diffs.size() == 0);
				
	}
	
	@Test
	public void emptyText() throws Exception {
		
		Long id = 100L;
		
		Response response = given()
				.get("http://localhost:8080/v1/diff/"+ id);
		
		response.print();
		
		JsonPath json = response.jsonPath();
		
		Integer statusCode = response.getStatusCode();
		String excpetion = json.getString("exception");
		String message = json.getString("message");
		Integer code = json.getInt("code");
		
		assertEquals(new Integer( HttpStatus.PRECONDITION_REQUIRED.value()), statusCode);
		assertEquals("waes.task.exception.PreconditionException", excpetion);
		assertEquals("No text with given id",message);
		assertEquals(new Integer( HttpStatus.PRECONDITION_REQUIRED.value()),code);
		
	}
	
	@Test
	public void leftTextEmpty() throws Exception {
		Long id = 15L;
	
		Text text = new Text();
		text.setId(id);
		String rightText = "0123**6789abcdefg";
		text.setRightText(rightText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/right");
		
		response = given()
				.get("http://localhost:8080/v1/diff/"+ id);
		
		response.print();
		
		JsonPath json = response.jsonPath();
		
		Integer statusCode = response.getStatusCode();
		String excpetion = json.getString("exception");
		String message = json.getString("message");
		Integer code = json.getInt("code");
		
		assertEquals(new Integer( HttpStatus.PRECONDITION_REQUIRED.value()), statusCode);
		assertEquals("waes.task.exception.PreconditionException", excpetion);
		assertEquals("Left text should not be null",message);
		assertEquals(new Integer( HttpStatus.PRECONDITION_REQUIRED.value()),code);
		
	}
	
	
	@Test
	public void rightTextEmpty() throws Exception {
		
		Long id = 16L;
		
		Text text = new Text();
		text.setId(id);
		String leftText = "0123**6789abcdefg";
		text.setLeftText(leftText);
		
		String encodeString = Base64JsonUtil.encodeToString(text);		
		Response response = given()
				.contentType(ContentType.TEXT)
				.body(encodeString)
				.put("http://localhost:8080/v1/diff/"+ id +"/left");
		
		response = given()
				.get("http://localhost:8080/v1/diff/"+ id);
		
		response.print();
		
		JsonPath json = response.jsonPath();
		
		Integer statusCode = response.getStatusCode();
		String excpetion = json.getString("exception");
		String message = json.getString("message");
		Integer code = json.getInt("code");
		
		assertEquals(new Integer( HttpStatus.PRECONDITION_REQUIRED.value()), statusCode);
		assertEquals("waes.task.exception.PreconditionException", excpetion);
		assertEquals("Right text should not be null",message);
		assertEquals(new Integer( HttpStatus.PRECONDITION_REQUIRED.value()),code);
	}
	
	
}
