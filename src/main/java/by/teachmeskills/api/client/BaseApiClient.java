package by.teachmeskills.api.client;

import by.teachmeskills.util.PropertiesLoader;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {
    static final String BASE_URL = "https://api.qase.io";

    BaseApiClient() {
        //        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    private RequestSpecification getRequestSpecification() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", PropertiesLoader.loadProperties().getProperty("token"));
    }

    Response post(String path, Map<String, ?> parameterNameValuePairs, Object body) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    Response patch(String path, Map<String, ?> parameterNameValuePairs, Object body) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .body(body)
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    Response patch(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response delete(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .pathParams(parameterNameValuePairs)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }
}
