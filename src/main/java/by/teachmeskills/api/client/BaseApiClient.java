package by.teachmeskills.api.client;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseApiClient {
    private static final String BASE_URL = "https://api.qase.io/v1/defect/";

    public BaseApiClient() {
        //        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URL;
    }

    protected RequestSpecification getRequestSpecification() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token", "a11dd8948d996ace5a534914241fea0571701e8a39061e07048e19f0bc9e4527");
    }

    public Response post(String path, Object body) {
        return getRequestSpecification()
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
    }

    public Response patch(String path, Object body) {
        return getRequestSpecification()
                .body(body)
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    public Response patch(String path) {
        return getRequestSpecification()
                .when()
                .patch(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path, Map<String, ?> parameterNameValuePairs) {
        return getRequestSpecification()
                .queryParams(parameterNameValuePairs)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response get(String path) {
        return getRequestSpecification()
                .when()
                .get(path)
                .then()
                .extract()
                .response();
    }

    public Response delete(String path) {
        return getRequestSpecification()
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
    }
}
