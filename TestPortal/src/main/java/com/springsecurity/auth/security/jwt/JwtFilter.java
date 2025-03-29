package com.springsecurity.auth.security.jwt;

import com.springsecurity.auth.repository.JwtTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springsecurity.auth.security.jwtservice.UserDetailsServiceImp;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtService;
    private final UserDetailsServiceImp userDetailsServiceImp;
    private  final JwtTokenRepository activeTokenRepository;

    JwtFilter(JwtUtils jwtService, UserDetailsServiceImp userDetailsServiceImp, JwtTokenRepository activeTokenRepository){
        this.jwtService=jwtService;
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.activeTokenRepository = activeTokenRepository;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull  HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);


        if (activeTokenRepository.findByUsername(username).isPresent()) {
            String activeToken = activeTokenRepository.findByUsername(username).get().getJwtToken();
            if (!activeToken.equals(token)) {
                throw new RuntimeException("Invalid token: You have logged in from another device.");
            }
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication() ==null){

            UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);
            if (jwtService.isValid(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request,response);

    }
}
