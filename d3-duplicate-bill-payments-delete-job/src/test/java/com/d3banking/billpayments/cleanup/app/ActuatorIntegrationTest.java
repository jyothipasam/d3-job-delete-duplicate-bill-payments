package com.d3banking.billpayments.cleanup.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActuatorIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void healthCheck() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"status\":\"UP\""));
    }

    @Test
    public void authenticatedEnvCheck() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("client", "client");
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> entity = restTemplate.exchange("/actuator/env", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue(entity.getBody().contains("\"activeProfiles\":[\"test\"]"));
    }

    @Test
    public void unauthenticatedEnvCheckFailed() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/env", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void authenticatedLogLevelsCheck() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("client", "client");
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> entity = restTemplate.exchange("/actuator/loggers",
            HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue(entity.getBody().contains("\"com.d3banking\":{\"configuredLevel\":\"INFO\",\"effectiveLevel\":\"INFO\"}"));

        ResponseEntity<String> postResponse = restTemplate.exchange("/actuator/loggers/com.d3banking",
            HttpMethod.POST, new HttpEntity<>("{\"configuredLevel\": \"DEBUG\"}", headers), String.class);
        assertEquals(HttpStatus.NO_CONTENT, postResponse.getStatusCode());

        entity = restTemplate.exchange("/actuator/loggers",
            HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertTrue(entity.getBody().contains("\"com.d3banking\":{\"configuredLevel\":\"DEBUG\",\"effectiveLevel\":\"DEBUG\"}"));
    }

    @Test
    public void unauthenticatedLogLevelsCheck() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> entity = restTemplate.exchange("/actuator/loggers",
            HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }
}
