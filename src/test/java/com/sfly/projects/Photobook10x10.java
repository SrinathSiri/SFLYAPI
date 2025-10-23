package com.sfly.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfly.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

public class Photobook10x10 extends BaseTest {

    @Test
    public void Photobook10x10() throws Exception {
        test.get().info("Preparing payload...");

        // ✅ Use Jackson ObjectMapper for lenient JSON parsing
        File file = new File("C:\\Users\\v-srinath.sirimalla\\AutomationWorkspace\\RestAssuredPractice\\src\\test\\java\\com\\sfly\\payloads\\10x10 Photobook.json");
        ObjectMapper mapper = new ObjectMapper();

        // ✅ Jackson automatically tolerates duplicate keys and keeps the last value
        Map<String, Object> payload = mapper.readValue(file, Map.class);

        // ✅ Log pretty JSON payload in Extent Report for transparency
        String prettyPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        test.get().info("<pre>" + prettyPayload + "</pre>");

        test.get().info("Sending POST request to Project API...");

        // ✅ Send the request using Rest Assured
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

        // ✅ Log and assert
        test.get().info("Response Code: " + response.statusCode());
        test.get().info("Response Body: " + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 201, "Project creation failed!");
    }
}
