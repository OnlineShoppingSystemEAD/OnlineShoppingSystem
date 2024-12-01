package com.example.usermanagement.Controller;

import com.example.usermanagement.Dto.ChangePasswordReq;
import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Service.AuthService;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {

    private final OurUserDetailsService ourUserDetailsService;
    private final AuthService authService;

    public UserController(AuthService authService, OurUserDetailsService ourUserDetailsService) {
        this.authService = authService;
        this.ourUserDetailsService = ourUserDetailsService;
    }

    /**
     * Validates if the user is authorized to perform the action.
     *
     * @param id the ID of the resource
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @return ResponseEntity containing the validation response if unauthorized, otherwise null
     */
    private ResponseEntity<ReqRes> validateAndRespond(Integer id, Integer userId, String role) {
        ReqRes resp = new ReqRes();
        if (!id.equals(userId) && !"ADMIN".equals(role)) {
            resp.setStatusCode(401);
            resp.setMessage("User is not authorized to perform this action");
            return ResponseEntity.status(resp.getStatusCode()).body(resp);
        }
        return null;
    }

    /**
     * Retrieves all user profiles.
     *
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @return ResponseEntity containing the list of all user profiles
     */
    @GetMapping
    public ResponseEntity<ReqRes> getAllUserProfiles(@RequestParam Integer userId, @RequestParam String role) {
        ReqRes resp = ourUserDetailsService.getAllUserProfiles();
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Retrieves a user profile by ID.
     *
     * @param id the ID of the user profile to retrieve
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @return ResponseEntity containing the user profile
     */
    @GetMapping("/{id}/profile")
    public ResponseEntity<ReqRes> getUserProfileById(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String role) {
        ResponseEntity<ReqRes> validationResp = validateAndRespond(id, userId, role);
        if (validationResp != null) {
            return validationResp;
        }
        ReqRes resp = ourUserDetailsService.getUserProfileById(id);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Creates a new user profile.
     *
     * @param id the ID of the user profile to create
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @param userProfile the user profile data
     * @return ResponseEntity containing the created user profile
     */
    @PostMapping("/{id}/profile")
    public ResponseEntity<ReqRes> createUserProfile(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String role, @RequestBody UserProfile userProfile) {
        ResponseEntity<ReqRes> validationResp = validateAndRespond(id, userId, role);
        if (validationResp != null) {
            return validationResp;
        }
        ReqRes resp = ourUserDetailsService.createUserProfile(id, userProfile);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Updates an existing user profile.
     *
     * @param id the ID of the user profile to update
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @param userProfileDetails the updated user profile data
     * @param profilePicture the updated profile picture
     * @return ResponseEntity containing the updated user profile
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<ReqRes> updateUserProfile(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String role, @RequestPart("userProfileDetails") UserProfile userProfileDetails, @RequestPart("profilePicture") MultipartFile profilePicture) {
        ResponseEntity<ReqRes> validationResp = validateAndRespond(id, userId, role);
        if (validationResp != null) {
            return validationResp;
        }
        ReqRes resp = ourUserDetailsService.updateUserProfile(id, userProfileDetails, profilePicture);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Deletes a user profile.
     *
     * @param id the ID of the user profile to delete
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @return ResponseEntity indicating the result of the deletion
     */
    @DeleteMapping("/{id}/profile")
    public ResponseEntity<ReqRes> deleteUserProfile(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String role) {
        ResponseEntity<ReqRes> validationResp = validateAndRespond(id, userId, role);
        if (validationResp != null) {
            return validationResp;
        }
        ReqRes resp = ourUserDetailsService.deleteUserProfile(id);
        return ResponseEntity.status(resp.getStatusCode()).build();
    }

    /**
     * Changes the password of a user.
     *
     * @param id the ID of the user whose password is to be changed
     * @param userId the ID of the user making the request
     * @param role the role of the user making the request
     * @param changePasswordReq the password change request data
     * @return ResponseEntity containing the result of the password change
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<ReqRes> changePassword(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String role, @RequestBody ChangePasswordReq changePasswordReq) {
        ResponseEntity<ReqRes> validationResp = validateAndRespond(id, userId, role);
        if (validationResp != null) {
            return validationResp;
        }
        ReqRes resp = authService.changePassword(id, changePasswordReq);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Verifies the email of a user.
     *
     * @param reqRes the email verification request data
     * @return ResponseEntity containing the result of the email verification
     */
    @PostMapping("/verify-email")
    public ResponseEntity<ReqRes> verifyEmail(@RequestBody ReqRes reqRes){
        ReqRes resp = authService.verifyEmail(reqRes);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
}