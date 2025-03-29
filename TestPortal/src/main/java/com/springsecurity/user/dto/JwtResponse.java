package com.springsecurity.user.dto;

import com.springsecurity.user.model.User;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private User user;
    
}
