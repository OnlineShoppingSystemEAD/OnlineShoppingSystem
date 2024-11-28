package com.example.usermanagement;

import com.example.usermanagement.Dto.ReqRes;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

// NOTE: @SpringBootTest is used to test the application with a real server. Slow but real HTTP requests.
// NOTE: @AutoConfigureMockMvc is used to test the application with a mock server. Fast but no HTTP requests.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private static String createdUserId;
    private static String authToken;
    private static String refreshToken;
    private static String verificationCode;
    private static String resetPasswordToken;

    @Test
    @Disabled
    void aTestThatPasses() {
        assertTrue(true);
    }

    @Test
    @Disabled
    void testSignupWithUsernameAndPassword_ReqResAsRawString() {
        // Creating a request payload exactly like Postman
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

        // Don't deserialize the response to ReqRes, it will give birth to unforeseen demons!
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

    @Test
    @Disabled
    void testSignUpWithUsernameAndPasswordGetsAccepted() {
        String currentTime = String.valueOf(System.currentTimeMillis());
        String testEmail = "testuser_" + currentTime+ "@example.com";
        String testPassword = "testPassword";

        ReqRes signupRequest = new ReqRes();
        signupRequest.setEmail(testEmail);
        signupRequest.setPassword(testPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // parse the request to HttpEntity
        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signupRequest, headers);

        // Send POST request with TestResponse class
        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                "/auth/signup",
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        // Assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(200, responseEntity.getBody().getStatusCode());
        assertEquals("User Saved Successfully", responseEntity.getBody().getMessage());
        assertNotNull(responseEntity.getBody().getOurUsers());
        assertEquals(String.format("testuser_%s@example.com", currentTime), responseEntity.getBody().getOurUsers().getEmail());
    }


}

/**
 * Helper classes for deserializing the response JSON.
 * These classes are used to deserialize the response JSON into Java objects.
 * Default deserialization is not possible because the response JSON is nested.
 */
@Setter
@Getter
class TestAuthority {
    private String authority;
}

@Setter
@Getter
class TestUser {
    private Long id;
    private String email;
    private String role;
    private List<TestAuthority> authorities;

}

@Setter
@Getter
class TestResponse {
    private int statusCode;
    private String message;
    private TestUser ourUsers;
}
