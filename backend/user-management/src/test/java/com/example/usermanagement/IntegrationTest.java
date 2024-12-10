package com.example.usermanagement;

import com.example.usermanagement.Dto.ReqRes;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

// NOTE: @SpringBootTest is used to test the application with a real server. Slow but real HTTP requests.
// NOTE: @AutoConfigureMockMvc is used to test the application with a mock server. Fast but no HTTP requests.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private static String createdUserId;
    private static String authToken;
    private static String refreshToken;
    private static String verificationCode;
    private static String resetPasswordToken;
    private static String testEmail;
    private static String testPassword;

    @Test
    void contextLoadsSuccessfully() {
        assertTrue(true, "Application context should load without errors");
    }

    @Test
    @Order(1)
    void testSignUpWithUniqueEmailAndStrongPasswordGetsAccepted() {
        testEmail = "testuser_" + UUID.randomUUID() + "@example.com";
        testPassword = Helper.generatePassword(
                12,
                2,
                2,
                2,
                2,
                FillMode.LOWER_CASE
        );

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
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatusCode());
    }

    @Test
    @Order(2)
    void testSignUpWithExistingUsernameAndStrongPasswordGetsRejected() {
        ReqRes signupRequest = new ReqRes();
        signupRequest.setEmail(testEmail);
        signupRequest.setPassword(testPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

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
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getBody().getStatusCode());
        assertNull(responseEntity.getBody().getOurUsers());
    }

    @Test
    @Order(3)
    void testSignInWithUsernameAndPasswordPasses() {

        testEmail = "user@example.com";
        testPassword = "password123";

        ReqRes signinRequest = new ReqRes();
        signinRequest.setEmail(testEmail);
        signinRequest.setPassword(testPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/auth/signIn")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");


        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signinRequest, headers);

        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        // Assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatusCode());
        assertNotNull(responseEntity.getBody().getToken());
        assertNotNull(responseEntity.getBody().getRefreshToken());

        // Store tokens for subsequent tests
        authToken = responseEntity.getBody().getToken();
        refreshToken = responseEntity.getBody().getRefreshToken();
    }

    @Test
    @Order(4)
    void testSignInWithIncorrectUsernameFails() {
        ReqRes signinRequest = new ReqRes();
        signinRequest.setEmail("random@email.com");
        signinRequest.setPassword("password123");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signinRequest, headers);

        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                "/auth/signIn",
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        // Assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatusCode());
    }

    @Test
    @Order(5)
    void testSignInWithIncorrectPasswordFails() {
        ReqRes signinRequest = new ReqRes();
        signinRequest.setEmail("user@example.com");
        signinRequest.setPassword("password12");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signinRequest, headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/auth/signIn")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");

        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        // Assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), responseEntity.getBody().getStatusCode());
    }

    @Test
    @Order(6)
    void testRefreshTokenPasses() {
        // Sign in part
        testEmail = "user@example.com";
        testPassword = "password123";

        ReqRes signinRequest = new ReqRes();
        signinRequest.setEmail(testEmail);
        signinRequest.setPassword(testPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/auth/signIn")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");


        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signinRequest, headers);

        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        refreshToken = responseEntity.getBody().getRefreshToken();

        // Refresh part
        ReqRes refreshTokenRequest = new ReqRes();
        refreshTokenRequest.setToken(refreshToken);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        requestEntity = new HttpEntity<>(refreshTokenRequest, headers);

        builder = UriComponentsBuilder.fromPath("/auth/token/refresh")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");

        responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        // Assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatusCode());
        assertNotNull(responseEntity.getBody().getToken());
    }

    @Test
    @Order(7)
    void testInvalidRefreshTokenFails() {
        // Signin part
        testEmail = "user@example.com";
        testPassword = "password123";

        ReqRes signinRequest = new ReqRes();
        signinRequest.setEmail(testEmail);
        signinRequest.setPassword(testPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/auth/signIn")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");


        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signinRequest, headers);

        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        refreshToken = responseEntity.getBody().getRefreshToken();

        // refresh        
        ReqRes refreshTokenRequest = new ReqRes();
        refreshTokenRequest.setToken("somegibberish");

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        requestEntity = new HttpEntity<>(refreshTokenRequest, headers);

        builder = UriComponentsBuilder.fromPath("/auth/token/refresh")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");

        responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );


        // Assertions
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getBody().getStatusCode());
    }

    @Test
    @Order(8)
    void testGetAllUsers() {
        // Signin part
        testEmail = "user@example.com";
        testPassword = "password123";

        ReqRes signinRequest = new ReqRes();
        signinRequest.setEmail(testEmail);
        signinRequest.setPassword(testPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/auth/signIn")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");


        HttpEntity<ReqRes> requestEntity = new HttpEntity<>(signinRequest, headers);

        ResponseEntity<TestResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                TestResponse.class
        );

        authToken = responseEntity.getBody().getToken();

        headers = new HttpHeaders();
        // headers.setBearerAuth("Bearer " + authToken);
        requestEntity = new HttpEntity<>(headers);

        builder = UriComponentsBuilder.fromPath("/users")
        .queryParam("userId", "3")
        .queryParam("role", "ADMIN")
        .queryParam("accStatus", "Verified");

        ResponseEntity<String> responseAllUsers = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        // Assertions
        assertEquals(HttpStatus.OK, responseAllUsers.getStatusCode());
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
    private String token;
    private String refreshToken;
}

class Helper {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_+=<>?";

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Generates a password with the specified parameters.
     *
     * @param length       Total length of the password.
     * @param numSymbols   Number of symbols to include.
     * @param numUppercase Number of uppercase letters to include.
     * @param numLowercase Number of lowercase letters to include.
     * @param numDigits    Number of digits to include.
     * @param fillMode     The character type to use for filling the remaining length ("uppercase", "lowercase", "symbols", "digits").
     * @return The generated password as a String.
     */
    public static String generatePassword(int length, int numSymbols, int numUppercase, int numLowercase, int numDigits, FillMode fillMode) {
        if (length < numSymbols + numUppercase + numLowercase + numDigits) {
            throw new IllegalArgumentException("Total length cannot be less than the sum of specified character counts.");
        }

        StringBuilder password = new StringBuilder(length);

        // Add specified counts of each character type
        password.append(generateRandomCharacters(SYMBOLS, numSymbols));
        password.append(generateRandomCharacters(UPPERCASE, numUppercase));
        password.append(generateRandomCharacters(LOWERCASE, numLowercase));
        password.append(generateRandomCharacters(DIGITS, numDigits));

        // Calculate remaining length to fill
        int remainingLength = length - password.length();
        String fillCharacters = getFillCharacters(fillMode);
        if (remainingLength > 0) {
            password.append(generateRandomCharacters(fillCharacters, remainingLength));
        }

        // Shuffle to randomize character positions
        return shufflePassword(password.toString());
    }

    private static String generateRandomCharacters(String characterSet, int count) {
        StringBuilder characters = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            characters.append(characterSet.charAt(RANDOM.nextInt(characterSet.length())));
        }
        return characters.toString();
    }

    private static String getFillCharacters(FillMode fillMode) {
        return switch (fillMode) {
            case FillMode.UPPER_CASE -> UPPERCASE;
            case FillMode.LOWER_CASE -> LOWERCASE;
            case FillMode.SPECIAL_CHARACTERS -> SYMBOLS;
            case FillMode.NUMBERS -> DIGITS;
            default ->
                    throw new IllegalArgumentException("Invalid fill mode. Choose from 'uppercase', 'lowercase', 'symbols', 'digits'.");
        };
    }

    private static String shufflePassword(String password) {
        char[] characters = password.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            // Swap characters
            char temp = characters[i];
            characters[i] = characters[index];
            characters[index] = temp;
        }
        return new String(characters);
    }

}

enum FillMode {
    UPPER_CASE, LOWER_CASE, MIXED_CASE, SPECIAL_CHARACTERS, NUMBERS
}

