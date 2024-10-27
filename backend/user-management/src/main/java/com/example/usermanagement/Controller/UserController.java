package com.example.usermanagement.Controller;

import com.example.usermanagement.Dto.ChangePasswordReq;
import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Service.AuthService;
import com.example.usermanagement.Service.EmailSenderService;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final OurUserDetailsService ourUserDetailsService;

    private final AuthService authService;

    private final EmailSenderService emailSenderService;

    public UserController(AuthService authService, OurUserDetailsService ourUserDetailsService, EmailSenderService emailSenderService) {
        this.authService = authService;
        this.ourUserDetailsService = ourUserDetailsService;
        this.emailSenderService = emailSenderService;

    }

    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
        return ourUserDetailsService.getAllUserProfiles();
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<ReqRes> getUserProfileById(@PathVariable Integer id) {
        ReqRes resp = ourUserDetailsService.getUserProfileById(id);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);

    }

    @PostMapping
    public ResponseEntity<ReqRes> createUserProfile(@RequestBody UserProfile userProfile) {
        ReqRes resp = ourUserDetailsService.createUserProfile(userProfile);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<ReqRes> updateUserProfile(@PathVariable Integer id, @RequestBody UserProfile userProfileDetails) {
        ReqRes resp = ourUserDetailsService.updateUserProfile(id, userProfileDetails);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    @DeleteMapping("/{id}/profile")
    public ResponseEntity<ReqRes> deleteUserProfile(@PathVariable Integer id) {
        ReqRes resp = ourUserDetailsService.deleteUserProfile(id);
        return ResponseEntity.status(resp.getStatusCode()).build();
    }

    @PostMapping("/{id}/reset-password")
    public ResponseEntity<ReqRes> changePassword(@PathVariable Integer id, @RequestBody ChangePasswordReq changePasswordReq) {
        ReqRes resp = authService.changePassword(id, changePasswordReq);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ReqRes> forgotPassword(@RequestBody ReqRes forgotPasswordReq){
        ReqRes resp = authService.forgotPassword(forgotPasswordReq);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    @PostMapping("/forgot-password/verify")
    public ResponseEntity<ReqRes> forgotPasswordVerify(@RequestBody ReqRes reqRes) {
        ReqRes resp = authService.forgotPasswordVerify(reqRes);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
    //just test post method to test email sending
    @PostMapping("/send-email")
    public ResponseEntity<ReqRes> sendEmail(@RequestBody ReqRes reqRes){
        emailSenderService.sendEmail("janithravisankax@gmail.com", "hi", "hello");
        return ResponseEntity.ok(reqRes);

    }

}