package com.example.usermanagement.Service;

import com.example.usermanagement.Dto.ReqRes;

import com.example.usermanagement.Entity.UserProfile;
import com.example.usermanagement.Repository.OurUserRepo;
import com.example.usermanagement.Repository.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class OurUserDetailsService implements UserDetailsService {

    private final OurUserRepo ourUserRepo;
    private final UserProfileRepo userProfileRepo;


    @Autowired
    public OurUserDetailsService(OurUserRepo ourUserRepo, UserProfileRepo userProfileRepo) {
        this.ourUserRepo = ourUserRepo;
        this.userProfileRepo = userProfileRepo;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ourUserRepo.findByEmail(username).orElseThrow();
    }

    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepo.findAll();
    }

    public ReqRes getUserProfileById(Integer id) {
        ReqRes resp = new ReqRes();
        try{
            Optional<UserProfile> userProfile = userProfileRepo.findById(id);
            if (userProfile.isPresent()) {
                resp.setStatusCode(200);
                resp.setMessage("User Profile Found");
                resp.setUserProfile(userProfile.get());
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User Profile Not Found");
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes createUserProfile(UserProfile userProfile) {
        ReqRes resp = new ReqRes();
        try {
            UserProfile userProfileResult = userProfileRepo.save(userProfile);
            if (userProfileResult.getId() > 0) {
                resp.setStatusCode(200);
                resp.setMessage("User Profile Saved Successfully");
                resp.setUserProfile(userProfileResult);
            }else{
                resp.setStatusCode(500);
                resp.setMessage("User Profile Not Saved");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes updateUserProfile(Integer id, UserProfile userProfileDetails) {

        ReqRes resp = new ReqRes();
        try {
            Optional<UserProfile> userProfileOptional = userProfileRepo.findById(id);
            if (userProfileOptional.isPresent()) {
                UserProfile userProfile = userProfileOptional.get();
                userProfile.setPostalCode(userProfileDetails.getPostalCode());
                userProfile.setContactNumber(userProfileDetails.getContactNumber());
                userProfile.setAddressLine1(userProfileDetails.getAddressLine1());
                userProfile.setAddressLine2(userProfileDetails.getAddressLine2());
                userProfile.setHouseNumber(userProfileDetails.getHouseNumber());
                userProfile.setProfilePicture(userProfileDetails.getProfilePicture());
                userProfile.setFirstName(userProfileDetails.getFirstName());
                userProfile.setLastName(userProfileDetails.getLastName());
                userProfile.setEmail(userProfileDetails.getEmail());
                UserProfile userProfileResult = userProfileRepo.save(userProfile);
                resp.setStatusCode(200);
                resp.setMessage("User Profile Updated Successfully");
                resp.setUserProfile(userProfileResult);
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User Profile Not Found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteUserProfile(Integer id) {

        ReqRes resp = new ReqRes();
        try {
            if (userProfileRepo.existsById(id)) {
                userProfileRepo.deleteById(id);
                resp.setStatusCode(200);
                resp.setMessage("User Profile Deleted Successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("User Profile Not Found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    //    change password



}
