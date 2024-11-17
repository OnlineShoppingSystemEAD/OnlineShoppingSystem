//package com.example.usermanagement.IntegrationTests;
//
//import com.example.usermanagement.Dto.ReqRes;
//import com.example.usermanagement.Entity.OurUsers;
//import com.example.usermanagement.Repository.OurUserRepo;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Random;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//public class AdminFlowIT {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private OurUserRepo ourUserRepo;
//
//    private OurUsers testAdmin;
//    private OurUsers testUser;
//
//    @BeforeEach
//    void setup() {
//        // Create a test admin and a test user
//        testAdmin = new OurUsers();
//        testAdmin.setEmail("testadmin@example.com");
//        testAdmin.setPassword("AdminPassword123!");
//        testAdmin.setRole("ADMIN");
//        testAdmin.setVerificationCode(String.valueOf(new Random().nextInt(900000) + 100000));
//        testAdmin.setIsVerified(true);
//        ourUserRepo.save(testAdmin);
//
//        testUser = new OurUsers();
//        testUser.setEmail("testuser@example.com");
//        testUser.setPassword("Password123!");
//        testUser.setRole("USER");
//        testUser.setVerificationCode(String.valueOf(new Random().nextInt(900000) + 100000));
//        testUser.setIsVerified(false);
//        ourUserRepo.save(testUser);
//    }
//
//    @AfterEach
//    void teardown() {
//        // Clean up test data
//        ourUserRepo.delete(testAdmin);
//        ourUserRepo.delete(testUser);
//    }
//
//    @Test
//    @WithMockUser(username = "testadmin@example.com", roles = "ADMIN")
//    public void testListUsers_AsAdmin_Returns200() throws Exception {
//        sendRequest(MockMvcRequestBuilders.get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    public void testListUsers_AsNonAdmin_Returns403() throws Exception {
//        sendRequest(MockMvcRequestBuilders.get("/users"))
//                .andExpect(status().isForbidden());
//    }
//
//    private void sendRequest(RequestBuilder requestBuilder) throws Exception {
//        this.webApplicationContext.getBean(MockMvc.class)
//                .perform(requestBuilder)
//                .andExpect(status().isOk());
//    }
//}