package com.springsecurity.userresultquestion.controller;

import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.userresultquestion.service.UserResultQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/result")
public class AdminResultQuestionController {
    private final UserResultQuestionService userResultQuestionService;

    public AdminResultQuestionController(UserResultQuestionService userResultQuestionService) {
        this.userResultQuestionService = userResultQuestionService;
    }

    @DeleteMapping("/exam/set/{examSetId}/{userId}")
    public ResponseEntity<String> getExamSetQuestions(@PathVariable Integer examSetId,@PathVariable Long userId) throws UserException {
        userResultQuestionService.deleteUserResultQuestions(Long.valueOf(examSetId),userId);
        return ResponseEntity.ok("deleted Successfully");
    }
}
