package com.dev.sfly.identity;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class GenerateToken {

    public static String getAutomationToken() {
        // Base URI
        RestAssured.baseURI = "https://identity-int3.dev.shutterfly.com";

        // Request body
        JSONObject body = new JSONObject();
        body.put("environment", "dev");
        body.put("jobName", "IDENTITY-TAF");
        body.put("managedBy", "sfly3-accounts");
        body.put("service", "Identity-service");

        // Cookie header from curl
        String cookieHeader = "sfly3_analytics=a; sfly3_trk=2.0; "
                + "CognitoIdentityServiceProvider.7jolpe9o8hgbe25vk1j7uushir.LastAuthUser=aurora_slfy_qa_testing734298%40yopmail.com; "
                + "CognitoIdentityServiceProvider.7jolpe9o8hgbe25vk1j7uushir.aurora_slfy_qa_testing734298%40yopmail.com.clockDrift=0; "
                + "CognitoIdentityServiceProvider.7jolpe9o8hgbe25vk1j7uushir.aurora_slfy_qa_testing734298%40yopmail.com.idToken=eyJraWQiOiJYYVdcL2Q1RzVnU3Y3dU9yTFwvQllkQXEzMkdXU3hnSFwvK1EzMDBhYjRVdUd3PSIsImFsZyI6IlJTMjU2In0..."
                + "; sfly3_optin=Beta; sfly3_trk=3.0; sfly3_analytics=b; "
                + "CognitoIdentityServiceProvider.7jolpe9o8hgbe25vk1j7uushir.LastAuthUser=srinathdev3test%40yopmail.com; "
                + "CognitoIdentityServiceProvider.7jolpe9o8hgbe25vk1j7uushir.srinathdev3test%40yopmail.com.clockDrift=0; "
                + "CognitoIdentityServiceProvider.7jolpe9o8hgbe25vk1j7uushir.srinathdev3test%40yopmail.com.idToken=eyJraWQiOiJYYVdcL2Q1RzVnU3Y3dU9yTFwvQllkQXEzMkdXU3hnSFwvK1EzMDBhYjRVdUd3PSIsImFsZyI6IlJTMjU2In0..."
                + "; JSESSIONID=node0pmag4vc0rkwy56ztqvlheaqt0.node0";

        // Send POST request
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", cookieHeader)
                .body(body.toString())
                .when()
                .post("/v3/automation/generateToken")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Extract token from response
        String token = response.jsonPath().getString("token");
        System.out.println("✅ Automation Token generated successfully: " + token);

        return token;
    }

   /* public static void main(String[] args) {
        getAutomationToken();
    }*/
}
