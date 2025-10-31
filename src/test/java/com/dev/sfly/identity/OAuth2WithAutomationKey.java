package com.dev.sfly.identity;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OAuth2WithAutomationKey {

    public static String getIdToken() {

        // Get automation key dynamically (if applicable)
        String automationKey = GenerateToken.getAutomationToken();

        // Base URI updated to match the new one
        RestAssured.baseURI = "https://identity-int3.dev.shutterfly.com";

        // Prepare and send POST request
        Response response = given()
                .header("Authorization", "Basic N2pvbHBlOW84aGdiZTI1dmsxajd1dXNoaXI6N2pvbHBlOW84aGdiZTI1dmsxajd1dXNoaXI=")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("clientid", "7jolpe9o8hgbe25vk1j7uushir")
                .header("Host", "identity-int3.stage.shutterfly.com")
                // If cookies are required, you can add them here:
                // .header("Cookie", "sfly3_analytics=a; sfly3_trk=2.0; ...")
                .formParam("grant_type", "password")
                .formParam("username", "srinathdev3test@yopmail.com")
                .formParam("password", "testing@123")
                .formParam("context", "/sfly/shutterfly/shutterfly-us/default")
                .formParam("domainOrigin", "accounts.shutterfly.com")
                .formParam("automationKey", automationKey)
                .when()
                .post("/v3/oauth2")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Extract and return id_token
        String idToken = response.jsonPath().getString("id_token");
        System.out.println("ID Token: " + idToken);
        return idToken;
    }

    /*public static void main(String[] args) {
        getIdToken();
    }*/
}
