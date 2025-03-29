package com.springsecurity.admin.service.imp;

import java.util.Optional;

import com.springsecurity.admin.service.UserService;
import com.springsecurity.auth.model.ActiveToken;
import com.springsecurity.auth.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.user.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public User getUserDetails(String username) throws UserException {
        User checkUser = repository.findByUsername(username).orElse(null);
        if(checkUser==null){
            throw new UserException("Username Not Found");
        }
       return checkUser;
    }

    @Override
    public Page<User> getAllUser(Pageable pageable) {
       return repository.findAll(pageable);
    }

    @Override
    public Page<User> getUserByName(String name,Pageable pageable) {
        System.out.println(name);
       return repository.findByName(name,pageable);
    }

    @Override
    public void removeUserById(Integer userId) throws UserException {
        User checkUser = repository.findById(userId).orElse(null);
        if (checkUser==null) {
            throw new UserException("Username Not Found");
        }
        repository.deleteById(userId);
        return;
    }

    @Override
    public boolean isValidToken(String username,String token) {
        ActiveToken user = jwtTokenRepository.findByUsername(username).orElse(null);
        if (user==null){
            return false;
        }
        if(user.getJwtToken().equals(token)){
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> getUserByUserId(Integer userId) {
        return repository.findById(userId);
    }

}
