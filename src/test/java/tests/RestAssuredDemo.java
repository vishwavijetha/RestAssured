package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class RestAssuredDemo {

	static final String baseURI = "https://reqres.in/";

	@Test
	public void get1() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		given().baseUri(baseURI).headers(headers).when().get("/api/users?page=2").then()
				.body("data.first_name", hasItems("Michael", "Lindsay")).statusCode(200);
	}

	@Test
	public void get2() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		String response = given().baseUri(baseURI).headers(headers).when().get("/api/users/2").getBody()
				.asPrettyString();
		System.out.println(response);

	}

	@Test
	public void get3() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		given().baseUri(baseURI).headers(headers).when().get("/api/users/23").then().statusCode(404);
		// This would Fail because of 404 not found
	}

	@Test
	public void post1() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		Map<String, String> map = new HashMap<>();
		map.put("name", "Vishwa");
		map.put("job", "Lead");
		JSONObject body = new JSONObject(map);

		given().baseUri(baseURI).headers(headers).body(body.toJSONString()).when().post("/api/users").then()
				.statusCode(201).and().log().status().and().log().body();
	}

	@Test
	public void post2() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		Map<String, String> map = new HashMap<>();
		map.put("email", "Vishwa@google.com");
		JSONObject body = new JSONObject(map);

		given().baseUri(baseURI).headers(headers).body(body.toJSONString()).when().post("/api/register").then()
				.statusCode(400). // this would fail -- missing password
				and().log().status().and().log().body();
	}

	@Test
	public void put() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		Map<String, String> map = new HashMap<>();
		map.put("name", "Vishwa");
		map.put("job", "Manager");
		JSONObject body = new JSONObject(map);

		given().baseUri(baseURI).headers(headers).body(body.toJSONString()).when().put("/api/users/2").then()
				.statusCode(200).and().log().status().and().log().body();

	}

	@Test
	public void patch() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		Map<String, String> map = new HashMap<>();
		map.put("name", "Vishwa");
		map.put("job", "Senior Manager");
		JSONObject body = new JSONObject(map);

		given().baseUri(baseURI).headers(headers).body(body.toJSONString()).when().patch("/api/users/2").then()
				.statusCode(200).and().log().status().and().log().body();

	}

	@Test
	public void delete() {

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		given().baseUri(baseURI).headers(headers).when().delete("/api/users/2").then().log().status().and().log()
				.body();

	}
}
