package com.example.usermanagement.Repository;

import com.example.usermanagement.Entity.UserProfile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserProfileRepoTest {

    @Autowired
    private UserProfileRepo userProfileRepo;

    private UserProfile testUserProfile;

    @BeforeEach
    public void setUp() {

        testUserProfile = new UserProfile();
        testUserProfile.setName("Test User");
        testUserProfile.setEmail("testuser@example.com");
        testUserProfile.setAddress("123 Test St");

        userProfileRepo.save(testUserProfile);
    }

    @Test
    public void testFindById_ProfileExists() {

        Optional<UserProfile> foundProfile = userProfileRepo.findById(testUserProfile.getId());

        assertTrue(foundProfile.isPresent(), "User profile should be found");
        assertEquals(testUserProfile.getName(), foundProfile.get().getName());
        assertEquals(testUserProfile.getEmail(), foundProfile.get().getEmail());
        assertEquals(testUserProfile.getAddress(), foundProfile.get().getAddress());
    }

    @Test
    public void testSave_Profile() {

        UserProfile newProfile = new UserProfile();
        newProfile.setName("New User");
        newProfile.setEmail("newuser@example.com");
        newProfile.setAddress("456 New St");

        UserProfile savedProfile = userProfileRepo.save(newProfile);

        assertEquals("New User", savedProfile.getName());
        assertEquals("newuser@example.com", savedProfile.getEmail());
        assertEquals("456 New St", savedProfile.getAddress());
    }

    @Test
    public void testDelete_Profile() {

        userProfileRepo.deleteById(testUserProfile.getId());

        Optional<UserProfile> deletedProfile = userProfileRepo.findById(testUserProfile.getId());
        assertFalse(deletedProfile.isPresent(), "User profile should be deleted");
    }
}
