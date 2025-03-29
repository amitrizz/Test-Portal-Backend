package com.springsecurity.examaccess.controller;

import com.springsecurity.admin.service.UserService;
import com.springsecurity.auth.security.jwt.JwtUtils;
import com.springsecurity.examaccess.model.ExamAccess;
import com.springsecurity.examaccess.service.ExamAccessService;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.Role;
import com.springsecurity.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/exam/access")
public class ExamAccessController {
    private final ExamAccessService examAccessService;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public ExamAccessController(ExamAccessService examAccessService, JwtUtils jwtUtils, UserService userService) {
        this.examAccessService = examAccessService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping("/exams")
    public ResponseEntity<List<Integer>> getAllUser(@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(examAccessService.getAllExamsAccess(Long.valueOf(user.getId()),true));
    }

    @GetMapping
    public ResponseEntity<String> removeExamAccess(@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        if (user.getRole().equals(Role.ADMIN)){
            System.out.println("Admin Permission");
            examAccessService.removeExpiredExamAccess();
            return ResponseEntity.ok("Data is update Successfully");
        }
        throw new UserException("User Have Not Permission");
    }

    @GetMapping("/by-id/{examAccessId}")
    public ResponseEntity<ExamAccess> getExamById(@PathVariable Integer examAccessId) throws UserException {
        return ResponseEntity.ok(examAccessService.getExamAccessById(Long.valueOf(examAccessId)));
    }

    @PostMapping("/exam-access")
    public ResponseEntity<ExamAccess> createExam(@RequestBody ExamAccess examAccess) throws UserException {
        return ResponseEntity.ok(examAccessService.createExamAccess(examAccess));
    }

    @PutMapping("/update-exam/{examAccessId}")
    public ResponseEntity<ExamAccess> updateExam(@RequestBody ExamAccess examAccess, @PathVariable Integer examAccessId) throws UserException {
        return ResponseEntity.ok(examAccessService.updateExamAccess(examAccess, Long.valueOf(examAccessId)));
    }

    @DeleteMapping("/delete-exam/{examAccessId}")
    public ResponseEntity<String> deleteExam(@PathVariable Integer examAccessId) throws UserException {
        examAccessService.deleteExamAccess(Long.valueOf(examAccessId));
        return ResponseEntity.ok("Exam deleted Successfully");
    }

}
