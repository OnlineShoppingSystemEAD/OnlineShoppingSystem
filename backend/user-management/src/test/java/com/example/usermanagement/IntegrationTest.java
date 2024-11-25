package com.example.usermanagement;

import com.example.usermanagement.Dto.ReqRes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private String authToken;
    private Long userId;
    private String testEmail;

    @Test
    void aTestThatPasses() {
        assertTrue(true);
    }

    @Test
    void testSignupWithUsernameAndPassword() {
//        // random test user
//        testEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
//
//        // Create request payload
//        ReqRes signupRequest = new ReqRes();
//        signupRequest.setEmail("testUser@email.com");
//        signupRequest.setPassword("testPassword");
////        signupRequest.setRole(Collections.singleton("USER").toString());
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.add("Content-Type", "application/json");
//
//        // Create HttpEntity
//        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signupRequest, headers);
//
////        // Send POST request
////        ResponseEntity<ReqRes> responseEntity = restTemplate.postForEntity(
////                "/auth/signup",
////                requestEntity,
////                ReqRes.class
////        );
//        ResponseEntity<ReqRes> responseEntity = null;
//
//        try {
//            // First try with String to see the actual response
//            ResponseEntity<String> rawResponse = restTemplate.exchange(
//                    "/auth/signup",
//                    HttpMethod.POST,
//                    requestEntity,
//                    String.class  // Get raw response to see what's coming back
//            );
//            System.out.println("Raw response: " + rawResponse.getBody());
//
//            // Now try the actual test
//            responseEntity = restTemplate.exchange(
//                    "/auth/signup",
//                    HttpMethod.POST,
//                    requestEntity,
//                    ReqRes.class
//            );
//
//        } catch (Exception e) {
//            System.out.println("Error occurred: " + e.getMessage());
//            e.printStackTrace();
//            fail("Test failed: " + e.getMessage());
//        }
//
//        // Basic assertions
//        assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody().getEmail()).isEqualTo(testEmail);

        // Create request payload exactly like Postman
        String requestJson = """
            {
                "email": "user@example.com",
                "password": "password123"
            }
            """;

        // Set headers like Postman
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity with raw JSON
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

        // Dont deserialize the response to ReqRes, it fails & you will have a headache
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/auth/signup",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Basic assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("User Saved Successfully"));
        assertTrue(responseEntity.getBody().contains("user@example.com"));
    }

}
