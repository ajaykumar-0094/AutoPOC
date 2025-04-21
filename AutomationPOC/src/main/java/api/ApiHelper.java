package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiHelper {

    public static Response get(String baseUrl, String token, String path) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", token)
                .when()
                .get(path);
    }

    public static Response post(String baseUrl, String token, String path, String requestBody) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(path);
    }

    public static Response delete(String baseUrl, String token, String path) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .header("Authorization", token)
                .when()
                .delete(path);
    }
}
