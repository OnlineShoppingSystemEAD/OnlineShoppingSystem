package com.example.usermanagement.IntegrationTests;

import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.OurUsers;
import com.example.usermanagement.Repository.OurUserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthFlowIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OurUserRepo ourUserRepo;

    private OurUsers testUser;

    @BeforeEach
    void setup() {
        // Create a test user
        testUser = new OurUsers();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("Password123!");
        testUser.setRole("USER");
        testUser.setVerificationCode(String.valueOf(new Random().nextInt(900000) + 100000));
        testUser.setIsVerified(false);
        ourUserRepo.save(testUser);
    }

    @AfterEach
    void teardown() {
        // Clean up test data
        ourUserRepo.delete(testUser);
    }

    @Test
    public void testSignUp_WithValidData_Returns200() throws Exception {
        ReqRes reqRes = new ReqRes();
        reqRes.setName("Test User");
        reqRes.setEmail("testuser2@example.com");
        reqRes.setPassword("Password123!");

        sendRequest(MockMvcRequestBuilders.post("/auth/signup"), reqRes)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    public void testSignIn_WithValidCredentials_Returns200() throws Exception {
        ReqRes reqRes = new ReqRes();
        reqRes.setEmail(testUser.getEmail());
        reqRes.setPassword(testUser.getPassword());

        sendRequest(MockMvcRequestBuilders.post("/auth/signin"), reqRes)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").isNotEmpty());
    }

    @Test
    public void testVerifyEmail_WithValidCode_Returns200() throws Exception {
        ReqRes reqRes = new ReqRes();
        reqRes.setEmail(testUser.getEmail());
        reqRes.setVerificationCode(testUser.getVerificationCode());

        sendRequest(MockMvcRequestBuilders.post("/auth/verify-email"), reqRes)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Email verified successfully"));
    }

    @Test
    public void testForgotPassword_WithValidEmail_Returns200() throws Exception {
        ReqRes reqRes = new ReqRes();
        reqRes.setEmail(testUser.getEmail());

        sendRequest(MockMvcRequestBuilders.post("/users/forgot-password"), reqRes)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password reset instructions sent to email"));
    }

    private void sendRequest(RequestBuilder requestBuilder, Object requestBody) throws Exception {
        String requestJson = asJson(requestBody);
        this.webApplicationContext.getBean(MockMvc.class)
                .perform(requestBuilder
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    private String asJson(Object obj) {
        // Convert the object to a JSON string
    }
}