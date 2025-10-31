package com.dev.sfly.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfly.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

public class Notepad5x7 extends BaseTest {

    @Test
    public void Notepad5x7() throws Exception {
        test.get().info("Preparing payload...");
        File file = new File("C:\\Users\\v-srinath.sirimalla\\AutomationWorkspace\\SFLYAPI\\src\\test\\java\\com\\sfly\\payloads\\5x7 Notepad.json");

        // ✅ Use Jackson instead of org.json
        ObjectMapper mapper = new ObjectMapper();

        // ✅ Jackson is tolerant — if JSON has duplicate keys, it just keeps the last one
        Map<String, Object> payload = mapper.readValue(file, Map.class);

        test.get().info("Sending POST request to Project API...");
        Response response = RestAssured.given()
                .baseUri(baseURI)
                .contentType("application/json")
                .header("Authorization", "Bearer " + idToken)
                .header("x-api-key", xApiKey)
                .body(payload)
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
