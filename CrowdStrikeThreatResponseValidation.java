import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CrowdStrikeThreatResponseValidation {

    private String baseUrl = "https://api.securitytool.com";  // Replace with actual URL
    private String apiKey = "your_api_key"; // Use secure storage in real use

    private String incidentId;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
    }

    @Test(priority = 1)
    public void simulateMalwareDetection() {
        String payload = """
        {
          "host": "WIN-TEST123",
          "threatType": "MALWARE",
          "signature": "EICAR-Test-File",
          "action": "simulate"
        }
        """;

        Response response = given()
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(payload)
                .post("/events");

        Assert.assertEquals(response.getStatusCode(), 201, "Threat simulation failed");

        incidentId = response.jsonPath().getString("incidentId");
        Assert.assertNotNull(incidentId, "Incident ID not returned");
    }

    @Test(priority = 2, dependsOnMethods = "simulateMalwareDetection")
    public void validatePostMitigationBehavior() throws InterruptedException {
        // Give system time to respond
        Thread.sleep(5000);

        Response response = given()
                .header("Authorization", "Bearer " + apiKey)
                .get("/incidents/" + incidentId);

        Assert.assertEquals(response.getStatusCode(), 200, "Failed to retrieve incident");

        String status = response.jsonPath().getString("status");
        String mitigation = response.jsonPath().getString("mitigationAction");

        Assert.assertEquals(status, "Resolved", "Incident not resolved");
        Assert.assertEquals(mitigation, "Quarantined", "Mitigation action not as expected");
    }
}