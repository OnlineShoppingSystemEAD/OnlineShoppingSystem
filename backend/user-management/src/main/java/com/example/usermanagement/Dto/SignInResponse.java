package com.example.usermanagement.Dto;


import lombok.Data;

@Data
public class SignInResponse {
    private Integer userId;
    private String email;
    private String role;
    private String token;
    private String refreshToken;
    private String expirationTime;

    // Constructors, getters, and setters
}

