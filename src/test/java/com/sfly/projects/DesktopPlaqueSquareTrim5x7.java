package com.sfly.projects;

import com.sfly.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;

public class DesktopPlaqueSquareTrim5x7 extends BaseTest {

    @Test
    public void DesktopPlaqueSquareTrim5x7() throws Exception {
        test.get().info("Preparing payload...");
        File file = new File("C:\\Users\\v-srinath.sirimalla\\AutomationWorkspace\\SFLYAPI\\src\\test\\java\\com\\sfly\\payloads\\5x7 Personalized Desktop Plaque - Square Trim.json");
        JSONObject payload = new JSONObject(new JSONTokener(new FileReader(file)));

        test.get().info("Sending POST request to Project API...");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .contentType("application/json")
                .header("Authorization", "Bearer " + idToken)
                .header("x-api-key", xApiKey)
                .body(payload.toString())
                .pathParam("project", "project")
                .queryParam("origin", "web_builder")
                .queryParam("accountId", accountId) // 🔥 dynamically loaded
                .when()
                .post("/projects/v2/{project}")
                .then()
                .extract().response();

        test.get().info("Response Code: " + response.statusCode());
        test.get().info("Response Body: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 201, "Project creation failed!");
    }
}
