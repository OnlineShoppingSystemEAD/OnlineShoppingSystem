package com.example.usermanagement.IntegrationTests;

import com.example.usermanagement.Dto.ChangePasswordReq;
import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.OurUsers;
import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Repository.OurUserRepo;
import com.example.usermanagement.Repository.UserProfileRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProfileFlowIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OurUserRepo ourUserRepo;

    @Autowired
    private UserProfileRepo userProfilesRepo;

    private OurUsers testUser;
    private UserProfile testUserProfile;

    @BeforeEach
    void setup() {
        // Create a test user and profile
        testUser = new OurUsers();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("Password123!");
        testUser.setRole("USER");
        testUser.setVerificationCode(String.valueOf(new Random().nextInt(900000) + 100000));
        testUser.setIsVerified(false);
        ourUserRepo.save(testUser);

        testUserProfile = new UserProfile();
        testUserProfile.setUser(testUser);
        testUserProfile.setPostalNumber("12345");
        testUserProfile.setPhoneNumber("1234567890");
        testUserProfile.setAddressPart1("123 Main St");
        testUserProfile.setAddressPart2("Anytown");
        testUserProfile.setAddressPart3("USA");
        userProfilesRepo.save(testUserProfile);
    }

    @AfterEach
    void teardown() {
        // Clean up test data
        userProfilesRepo.delete(testUserProfile);
        ourUserRepo.delete(testUser);
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = "USER")
    public void testUpdateProfile_WithValidData_Returns200() throws Exception {
        ReqRes reqRes = new ReqRes();
        reqRes.setName("Updated Test User");
        reqRes.setPhoneNumber("9876543210");
        reqRes.setAddressPart1("456 Oak St");
        reqRes.setAddressPart2("Somewhere");
        reqRes.setAddressPart3("USA");

        sendRequest(MockMvcRequestBuilders.put("/users/{id}/profile", testUser.getId()), reqRes)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User profile updated successfully"));
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = "USER")
    public void testChangePassword_WithValidData_Returns200() throws Exception {
        ChangePasswordReq changePasswordReq = new ChangePasswordReq();
        changePasswordReq.setCurrentPassword(testUser.getPassword());
        changePasswordReq.setNewPassword("NewPassword123!");

        sendRequest(MockMvcRequestBuilders.put("/users/{id}/password", testUser.getId()), changePasswordReq)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Password changed successfully"));
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