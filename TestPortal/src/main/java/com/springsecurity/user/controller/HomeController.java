package com.springsecurity.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.auth.security.jwt.JwtUtils;
import com.springsecurity.admin.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class HomeController {
    
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<User> profile(@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        return ResponseEntity.ok(userService.getUserDetails(username));
    }

    @GetMapping("/check-token")
    public ResponseEntity<Boolean> isValidUserToken(@RequestHeader("Authorization") String authToken){
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        if (username.isEmpty()){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(userService.isValidToken(username,token));
    }
}
