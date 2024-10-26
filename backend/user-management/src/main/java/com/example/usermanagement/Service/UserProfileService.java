package com.example.usermanagement.Service;

import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getUserProfileById(Integer id) {
        Optional<UserProfile> userProfile = userProfileRepository.findById(id);
        return userProfile.orElse(null);
    }

    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public UserProfile updateUserProfile(Integer id, UserProfile userProfileDetails) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
        if (userProfileOptional.isPresent()) {
            UserProfile userProfile = userProfileOptional.get();
            userProfile.setPostalNumber(userProfileDetails.getPostalNumber());
            userProfile.setPhoneNumber(userProfileDetails.getPhoneNumber());
            userProfile.setAddressPart1(userProfileDetails.getAddressPart1());
            userProfile.setAddressPart2(userProfileDetails.getAddressPart2());
            userProfile.setAddressPart3(userProfileDetails.getAddressPart3());
            return userProfileRepository.save(userProfile);
        } else {
            return null;
        }
    }

    public boolean deleteUserProfile(Integer id) {
        if (userProfileRepository.existsById(id)) {
            userProfileRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}