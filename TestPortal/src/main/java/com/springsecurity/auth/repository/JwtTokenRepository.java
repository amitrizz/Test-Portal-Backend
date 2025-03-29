package com.springsecurity.auth.repository;

import com.springsecurity.auth.model.ActiveToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<ActiveToken,Long> {
    Optional<ActiveToken> findByUsername(String username);
}
