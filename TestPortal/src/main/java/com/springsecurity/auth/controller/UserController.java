package com.springsecurity.auth.controller;

import com.springsecurity.user.dto.AuthenticationResponse;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final AuthService authenticationService;

    public UserController(AuthService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) throws Exception {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) throws UserException {
        return ResponseEntity.ok(authenticationService.login(request));
    }


}
