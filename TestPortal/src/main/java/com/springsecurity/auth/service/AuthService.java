package com.springsecurity.auth.service;

import com.springsecurity.auth.model.ActiveToken;
import com.springsecurity.auth.repository.JwtTokenRepository;
import com.springsecurity.user.dto.AuthenticationResponse;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.user.repository.UserRepository;
import com.springsecurity.auth.security.jwt.JwtUtils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtService;
    private final JwtTokenRepository jwtTokenRepository;

    private final AuthenticationManager authenticationManager;
    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtUtils jwtService, JwtTokenRepository jwtTokenRepository, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtTokenRepository = jwtTokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) throws UserException {
        User checkExistingUser = repository.findByUsername(request.getUsername()).orElse(null);

        if(checkExistingUser==null){
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            User savedUser = repository.save(user);

            String token = jwtService.generateToken(savedUser);
            return new AuthenticationResponse(savedUser,token);
        }
        throw new UserException("User is Already Existing");
    }

    public AuthenticationResponse login(User request) throws UserException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );

        User user = repository.findByUsername(request.getUsername()).orElse(null);
        if (user==null){
            throw new UserException("username or password is incorrect");
        }
        String token = jwtService.generateToken(user);
        jwtTokenRepository.findByUsername(request.getUsername()).ifPresent(jwtTokenRepository::delete);

        ActiveToken activeToken = new ActiveToken();
        activeToken.setUsername(request.getUsername());
        activeToken.setJwtToken(token);

        jwtTokenRepository.save(activeToken);

        return new AuthenticationResponse(user, token);
    }
}
