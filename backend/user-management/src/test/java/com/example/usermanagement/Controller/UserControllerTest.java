package com.example.usermanagement.Controller;

import com.example.usermanagement.Dto.ChangePasswordReq;
import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Service.AuthService;
import com.example.usermanagement.Service.EmailSenderService;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private OurUserDetailsService ourUserDetailsService;

    @Mock
    private AuthService authService;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private UserController userController;

    private ReqRes mockResponse;
    private UserProfile mockProfile;
    private ChangePasswordReq mockPasswordReq;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockResponse = new ReqRes();
        mockResponse.setStatusCode(200);
        mockResponse.setMessage("Success");

        mockProfile = new UserProfile();
        mockProfile.setId(1);

        mockPasswordReq = new ChangePasswordReq();
        mockPasswordReq.setCurrentPassword("currentPassword");
        mockPasswordReq.setNewPassword("newPassword");
    }

    @Test
    public void testGetAllUserProfiles() {
        List<UserProfile> mockProfiles = Arrays.asList(mockProfile);
        when(ourUserDetailsService.getAllUserProfiles()).thenReturn(mockProfiles);

        List<UserProfile> result = userController.getAllUserProfiles();

        assertEquals(mockProfiles, result);
        verify(ourUserDetailsService, times(1)).getAllUserProfiles();
    }

    @Test
    public void testGetUserProfileById() {
        when(ourUserDetailsService.getUserProfileById(1)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.getUserProfileById(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testCreateUserProfile() {
        when(ourUserDetailsService.createUserProfile(mockProfile)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.createUserProfile(mockProfile);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testUpdateUserProfile() {
        when(ourUserDetailsService.updateUserProfile(1, mockProfile)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.updateUserProfile(1, mockProfile);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testDeleteUserProfile() {
        when(ourUserDetailsService.deleteUserProfile(1)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.deleteUserProfile(1);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testChangePassword() {
        when(authService.changePassword(1, mockPasswordReq)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.changePassword(1, mockPasswordReq);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testForgotPassword() {
        when(authService.forgotPassword(mockResponse)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.forgotPassword(mockResponse);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testForgotPasswordVerify() {
        when(authService.forgotPasswordVerify(mockResponse)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.forgotPasswordVerify(mockResponse);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }

    @Test
    public void testSendEmail() {
        ReqRes reqRes = new ReqRes();
        reqRes.setEmail("test@example.com");

        ResponseEntity<ReqRes> result = userController.sendEmail(reqRes);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(emailSenderService, times(1)).sendEmail("janithravisankax@gmail.com", "hi", "hello");
    }

    @Test
    public void testVerifyEmail() {
        when(authService.verifyEmail(mockResponse)).thenReturn(mockResponse);

        ResponseEntity<ReqRes> result = userController.verifyEmail(mockResponse);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockResponse, result.getBody());
    }
}
