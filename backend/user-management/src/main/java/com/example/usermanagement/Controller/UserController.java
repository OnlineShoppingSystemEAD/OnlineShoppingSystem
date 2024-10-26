package com.example.usermanagement.Controller;

import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
        return userProfileService.getAllUserProfiles();
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Integer id) {
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public UserProfile createUserProfile(@RequestBody UserProfile userProfile) {
        return userProfileService.createUserProfile(userProfile);
    }


    @PutMapping("/{id}/profile")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Integer id, @RequestBody UserProfile userProfileDetails) {
        UserProfile updatedUserProfile = userProfileService.updateUserProfile(id, userProfileDetails);
        if (updatedUserProfile != null) {
            return ResponseEntity.ok(updatedUserProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/profile")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Integer id) {
        boolean isDeleted = userProfileService.deleteUserProfile(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}