package com.springsecurity.admin.service;

import java.util.Optional;

import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getUserDetails(String username) throws UserException;
    Page<User> getAllUser(Pageable pageable);
    Page<User> getUserByName(String name,Pageable pageable);
    void removeUserById(Integer userId) throws UserException;
    boolean isValidToken(String username,String token);
    Optional<User> getUserByUserId(Integer userId);

}
