package com.springsecurity.user.repository;

import com.springsecurity.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(last_name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<User> findByName(@Param("name") String name, Pageable pageable);
}
