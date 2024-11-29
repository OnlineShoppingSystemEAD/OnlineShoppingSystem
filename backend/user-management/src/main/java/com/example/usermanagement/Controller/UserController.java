package com.example.usermanagement.Controller;

import com.example.usermanagement.Dto.ChangePasswordReq;
import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Service.AuthService;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * Endpoint to get all user profiles.
     *
     * @return List of all user profiles
     */
    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
        return ourUserDetailsService.getAllUserProfiles();
    }

    /**
     * Endpoint to get a user profile by ID.
     *
     * @param id the ID of the user
     * @return ResponseEntity containing the user profile response
     */
    @GetMapping("/{id}/profile")
    public ResponseEntity<ReqRes> getUserProfileById(@PathVariable Integer id, @RequestParam Integer userId, @RequestParam String role) {
        ReqRes resp = ourUserDetailsService.getUserProfileById(id);

        System.out.println("userId: " + userId);
        System.out.println("role: " + role);

        return ResponseEntity.status(resp.getStatusCode()).body(resp);

    }

    /**
     * Endpoint to create a new user profile.
     *
     * @param userProfile the request body containing user profile details:
     *                    - firstName: the user's first name
     *                    - lastName: the user's last name
     *                    - email: the user's email address
     *                    - postalCode: the user's postal code
     *                    - contactNumber: the user's contact number
     *                    - houseNumber: the user's house number
     *                    - addressLine1: the first line of the user's address
     *                    - addressLine2: the second line of the user's address
     *                    - profilePicture: the URL of the user's profile picture
     * @return ResponseEntity containing the create user profile response
     */
    @PostMapping("/{id}/profile")
    public ResponseEntity<ReqRes> createUserProfile(@PathVariable Integer id,@RequestBody UserProfile userProfile) {
        ReqRes resp = ourUserDetailsService.createUserProfile(id, userProfile);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }


    /**
     * Endpoint to update a user profile by ID.
     *
     * @param id the ID of the user
     * @param userProfileDetails the request body containing user profile details:
     *                           - firstName: the user's first name
     *                           - lastName: the user's last name
     *                           - email: the user's email address
     *                           - postalCode: the user's postal code
     *                           - contactNumber: the user's contact number
     *                           - houseNumber: the user's house number
     *                           - addressLine1: the first line of the user's address
     *                           - addressLine2: the second line of the user's address
     * @param profilePicture the MultipartFile containing the user's profile picture
     * @return ResponseEntity containing the update user profile response
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<ReqRes> updateUserProfile(@PathVariable Integer id, @RequestPart("userProfileDetails") UserProfile userProfileDetails, @RequestPart("profilePicture") MultipartFile profilePicture) {
        ReqRes resp = ourUserDetailsService.updateUserProfile(id, userProfileDetails, profilePicture);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }


    /**
     * Endpoint to delete a user profile by ID.
     *
     * @param id the ID of the user
     * @return ResponseEntity with the status of the delete operation
     */
    @DeleteMapping("/{id}/profile")
    public ResponseEntity<ReqRes> deleteUserProfile(@PathVariable Integer id) {
        ReqRes resp = ourUserDetailsService.deleteUserProfile(id);
        return ResponseEntity.status(resp.getStatusCode()).build();
    }

    /**
     * Endpoint to change a user's password.
     *
     * @param id the ID of the user
     * @param changePasswordReq the request body containing password change details:
     *                          - oldPassword: the user's current password
     *                          - newPassword: the user's new password
     * @return ResponseEntity containing the change password response
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<ReqRes> changePassword(@PathVariable Integer id, @RequestBody ChangePasswordReq changePasswordReq) {
        ReqRes resp = authService.changePassword(id, changePasswordReq);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Endpoint to initiate the forgot password process.
     *
     * @param forgotPasswordReq the request body containing forgot password details:
     *                          - email: the user's email address
     * @return ResponseEntity containing the forgot password response
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ReqRes> forgotPassword(@RequestBody ReqRes forgotPasswordReq){
        ReqRes resp = authService.forgotPassword(forgotPasswordReq);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Endpoint to verify the forgot password process.
     *
     * @param reqRes the request body containing verification details:
     *               - email: the user's email address
     *               - verificationCode: the verification code sent to the user's email
     *               - newPassword: the user's new password
     * @return ResponseEntity containing the forgot password verification response
     */
    @PostMapping("/forgot-password/verify")
    public ResponseEntity<ReqRes> forgotPasswordVerify(@RequestBody ReqRes reqRes) {
        ReqRes resp = authService.forgotPasswordVerify(reqRes);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    /**
     * Endpoint to verify the user's email.
     *
     * @param reqRes the request body containing email verification details:
     *               - email: the user's email address
     *               - verificationCode: the verification code sent to the user's email
     * @return ResponseEntity containing the email verification response
     */
    @PostMapping("/verify-email")
    public ResponseEntity<ReqRes> verifyEmail(@RequestBody ReqRes reqRes){
        ReqRes resp = authService.verifyEmail(reqRes);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }


}