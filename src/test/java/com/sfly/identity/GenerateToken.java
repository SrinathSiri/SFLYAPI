package com.sfly.identity;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class GenerateToken {

    public static String getAutomationToken() {
        // Base URI
        RestAssured.baseURI = "https://identity-int3.stage.shutterfly.com";

        // Request body
        JSONObject body = new JSONObject();
        body.put("environment", "stage");
        body.put("jobName", "IDENTITY-TAF");
        body.put("managedBy", "sfly3-accounts");
        body.put("service", "Identity-service");

        // Send POST request
        Response response = given()
                .header("Authorization", "Bearer eyJjdHkiOiJ0ZXh0L3BsYWluIiwiZXhwIjoxNzU4NTE5MjI5Njk0LCJhbGciOiJSUzI1NiIsImtpZCI6ImE2ZmJmZDU3LWU5ODMtNDAyOS05ZDJmLTY4MzQ4YjZmMDIxYSJ9.eyJzdWIiOiJiNmQwN2ViODA3ZTI0YWQ2YWUyZTMwYTVkZDRkYjRmMiIsImUiOiJzZmx5LXN0ZyIsImlzcyI6InVybjpzZmx5MzphdXRoIiwicGlkIjoiL3NmbHkiLCJjcm9sZXMiOiJBRE0iLCJhdWQiOiJiNmQwN2ViODA3ZTI0YWQ2YWUyZTMwYTVkZDRkYjRmMiIsInNnIjoiYWNjb3VudHMtYWRtaW4iLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6InVybjpzZmx5Om9yZGVyL2NyZWF0ZSB1cm46c2ZseTpjYXJ0L293bmVyIHVybjpzZmx5Om9yZGVyL3JlYWQgdXJuOnNmbHk6cHJvamVjdHNlcnZpY2UvcmVhZCB1cm46c2ZseTp1c2VyL3JlYWQgdXJuOnNmbHk6b3JkZXIvdXBkYXRlIHVybjpzZmx5OnRtYWlsc2VydmljZS9lbWFpbCB1cm46c2ZseTpwaG90b3NzZXJ2aWNlcy9yZWFkIHVybjpzZmx5MzplbnRlcnByaXNlL2NyZWF0ZSB1cm46c2ZseTM6bm90aWZ5L2NyZWF0ZSB1cm46c2ZseTM6YWNjb3VudHNzZXJ2aWNlL2FkbWluIHVybjpzZmx5OnJldGFpbENvcmUvcmVhZCB1cm46c2ZseTpjb21Qcm9tb3Rpb24vcmVhZCB1cm46c2ZseTpjb21Qcm9tb3Rpb24vd3JpdGUgdXJuOnNmbHkzOnVzZXJzZWdtZW50YXRpb24vcmVhZCB1cm46c2ZseTM6bG9va3Vwc2VydmljZS9yZWFkIHVybjpzZmx5Mzpsb29rdXBzZXJ2aWNlL2NyZWF0ZSB1cm46c2ZseTM6YWNjb3VudHMvcmVhZCB1cm46c2ZseTM6YWNjb3VudHNzZXJ2aWNlL3JlYWQgdXJuOnNmbHkzOmFjY291bnRzL3VwZGF0ZSB1cm46c2ZseTM6YWNjb3VudHMvcmVhZCB1cm46c2ZseTM6YWNjb3VudHMvZGVsZXRlIHVybjpzZmx5MzphY2NvdW50cy9jcmVhdGUgdXJuOnNmbHkzOnByb2plY3RzZXJ2aWNlOnByb2plY3QvYnVsa3RyYW5zZmVyIHVybjpzZmx5MzptZWRpYXNlcnZpY2UvY3JlYXRlIiwiYXV0aF90aW1lIjoxNzU4NTE5MjI5LCJjb250ZXh0IjoiL3NmbHkvc2h1dHRlcmZseS9zaHV0dGVyZmx5LXVzL2RlZmF1bHQiLCJleHAiOjE3OTAwNTUyMjksImlhdCI6MTc1ODUxOTIyOSwiY2lkIjoiYjZkMDdlYjgwN2UyNGFkNmFlMmUzMGE1ZGQ0ZGI0ZjIiLCJ0cyI6MTc1ODUyMjgyOTY5M30.KLjhfxn-PFr13HOIMoRcOh0AwQ3NmkB3cUzIzeUpqOTN74u9legNgTjs5-5LkXEWA2RNrxA10EiDM8XZkw5j1G7I_yii4UqFmJpIkp1kM7JFNVSvvm8k2Oyt7NZEwG1SN9hq9WsfSoSsYfJh2axtzizq489ghIuLqf-8lZRfbVeXvOaIkvumX62GJjrnvBbkQcLM24LiNz3j8axm4uHmW_LNotTQSZ5T8vgxgU5lSN1ncW6vbsb-eSIj8X4vpogPG6mabFdB9Asw9GEuHQ_-hMxOR65HiGPayy1sMUWV5E5laIk1nVcQdEwCDEn4z4MKTcdBPvXAx9k7mgxNony3OA")
                .header("Content-Type", "application/json")
                .body(body.toString())
                .when()
                .post("/v3/automation/generateToken")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Extract token
        String token = response.jsonPath().getString("token");
        System.out.println("✅ Automation Token generated: " + token);

        return token;
    }
}
