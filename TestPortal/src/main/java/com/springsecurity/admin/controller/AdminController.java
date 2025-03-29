package com.springsecurity.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.admin.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/admin")
public class AdminController {

     @Autowired
    private UserService userService;

    @GetMapping("all-user")
    public ResponseEntity<Page<User>> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUser(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> userByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getUserByName(name,pageable));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeUser(@RequestParam Integer userid) throws UserException {
        userService.removeUserById(userid);
        
        return ResponseEntity.ok("User Deleted Successfully");
    }
    
    
    
}
