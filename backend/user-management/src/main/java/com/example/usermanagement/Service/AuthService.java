package com.example.usermanagement.Service;

import com.example.usermanagement.Dto.ChangePasswordReq;
import com.example.usermanagement.Dto.ReqRes;
import com.example.usermanagement.Entity.OurUsers;
import com.example.usermanagement.Repository.OurUserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Random;

import java.util.HashMap;

@Service
public class AuthService {
    private final OurUserRepo ourUserRepo;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailSenderService emailSenderService;

    public AuthService(
            OurUserRepo ourUserRepo,
            JWTUtils jwtUtils,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailSenderService emailSenderService
            ) {
        this.ourUserRepo = ourUserRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailSenderService = emailSenderService;
    }

    public ReqRes signUp(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUsers = new OurUsers();
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            OurUsers ourUserResult = ourUserRepo.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest){
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

    // changePassword method
    public ReqRes changePassword(Integer id , ChangePasswordReq changePasswordRequest){
        ReqRes response = new ReqRes();
        try {
            OurUsers ourUsers = ourUserRepo.findById(id).orElseThrow();
            if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), ourUsers.getPassword())) {
                ourUsers.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                ourUserRepo.save(ourUsers);
                response.setStatusCode(200);
                response.setMessage("Password Changed Successfully");
            }else {
                response.setStatusCode(500);
                response.setMessage("Current Password is Incorrect");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }


    public ReqRes forgotPassword(ReqRes forgotPasswordReq) {
        ReqRes response = new ReqRes();
        try {
            OurUsers ourUsers = ourUserRepo.findByEmail(forgotPasswordReq.getEmail()).orElseThrow();
            if (ourUsers != null) {
                // Generate a random verification code
                String verificationCode = generateVerificationCode();

                // Save the verification code in the OurUsers entity
                ourUsers.setVerificationCode(verificationCode);
                ourUserRepo.save(ourUsers);

                // Send the verification code to the user's email
                emailSenderService.sendEmail(ourUsers.getEmail(), "Password Reset Code", "Your Password Reset Code is: " + verificationCode);
                response.setStatusCode(200);
                response.setMessage("Password Reset Code Sent to Email");
            } else {
                response.setStatusCode(500);
                response.setMessage("Email Not Found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate a 6-digit random number
        return String.valueOf(code);
    }

    public ReqRes forgotPasswordVerify(ReqRes reqRes) {
        ReqRes response = new ReqRes();
        try {
            OurUsers ourUsers = ourUserRepo.findByEmail(reqRes.getEmail()).orElseThrow();
            if (ourUsers.getVerificationCode().equals(reqRes.getVerificationCode())) {
                //delete the verification code
                ourUsers.setVerificationCode(null);
                ourUsers.setPassword(passwordEncoder.encode(reqRes.getNewPassword()));
                ourUserRepo.save(ourUsers);
                response.setStatusCode(200);
                response.setMessage("Password Reset Successfully");
            } else {
                response.setStatusCode(401);
                response.setMessage("Verification Code is Incorrect");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;

    }

}
