package com.sfly.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfly.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class Photobook8x8 extends BaseTest {

    @Test
    public void Photobook8x8() throws Exception {
        test.get().info("Preparing payload...");

        // ✅ Use Jackson instead of org.json
        File file = new File("C:\\Users\\v-srinath.sirimalla\\AutomationWorkspace\\RestAssuredPractice\\src\\test\\java\\com\\sfly\\payloads\\8x8 Photobook.json");
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
