package com.sfly.identity;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OAuth2WithAutomationKey {

    public static String getIdToken() {

        String automationKey = GenerateToken.getAutomationToken();

        RestAssured.baseURI = "https://identity-int3.stage.shutterfly.com";

        Response response = given()
                .header("Authorization", "Basic Mjc5Ymd0NmdhY25obW45dWtndTYzcXF1aDM6cXdycg==")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", "srinathstage6@yopmail.com")
                .formParam("password", "Testing@123")
                .formParam("context", "/sfly/shutterfly/shutterfly-us/default")
                .formParam("domainOrigin", "accounts.shutterfly.com")
                .formParam("automationKey", automationKey)
                .when()
                .post("/v3/oauth2")
                .then()
                .statusCode(201)
                .extract()
                .response();

        return response.jsonPath().getString("id_token");
    }
}

