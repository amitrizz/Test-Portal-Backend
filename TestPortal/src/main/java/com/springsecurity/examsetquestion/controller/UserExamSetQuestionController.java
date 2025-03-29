package com.springsecurity.examsetquestion.controller;

import com.springsecurity.examsetquestion.model.Category;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.examsetquestion.service.ExamSetQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/exam/set/question")
public class UserExamSetQuestionController {
    private final ExamSetQuestionService examSetQuestionService;

    public UserExamSetQuestionController(ExamSetQuestionService examSetQuestionService) {
        this.examSetQuestionService = examSetQuestionService;
    }

    @GetMapping("/sets/{examSetId}/{category}")
    public ResponseEntity<List<ExamSetQuestion>> getAllExamSetCategoryWiseQuestion(@PathVariable Long examSetId, @PathVariable String category) {
        return ResponseEntity.ok(examSetQuestionService.getAllExamSetCategoryQuestion(examSetId,category));
    }
}
