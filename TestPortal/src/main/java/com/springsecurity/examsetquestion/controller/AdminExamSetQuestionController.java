package com.springsecurity.examsetquestion.controller;

import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.examsetquestion.service.ExamSetQuestionService;
import com.springsecurity.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/exam/set/question")
public class AdminExamSetQuestionController {

    private final ExamSetQuestionService examSetQuestionService;

    public AdminExamSetQuestionController(ExamSetQuestionService examSetQuestionService) {
        this.examSetQuestionService = examSetQuestionService;
    }

    @GetMapping("/by-id/{examSetQuestionId}")
    public ResponseEntity<ExamSetQuestion> getExamSetQuestionById(@PathVariable Integer examSetQuestionId) throws UserException {
        return ResponseEntity.ok(examSetQuestionService.getExamSetQuestionById(Long.valueOf(examSetQuestionId)));
    }

    @GetMapping("/all-set-question/{examSetId}")
    public ResponseEntity<List<ExamSetQuestion>> getExamSetQuestions(@PathVariable Integer examSetId) throws UserException {
        return ResponseEntity.ok(examSetQuestionService.getAllExamSetQuestions(examSetId));
    }

    @PostMapping("/create-exam")
    public ResponseEntity<ExamSetQuestion> createExam(@RequestBody ExamSetQuestion examSetQuestion) throws UserException {
        System.out.println("Working");
        return ResponseEntity.ok(examSetQuestionService.createExamSetQuestion(examSetQuestion));
    }

    @PutMapping("/update-exam/{examSetQuestionId}")
    public ResponseEntity<ExamSetQuestion> updateExam(@RequestBody ExamSetQuestion examSetQuestion,@PathVariable Integer examSetQuestionId) throws UserException {
        return ResponseEntity.ok(examSetQuestionService.updateExamSetQuestion(examSetQuestion, Long.valueOf(examSetQuestionId)));
    }

    @DeleteMapping("/delete-exam/{examSetQuestionId}")
    public ResponseEntity<String> deleteExam(@PathVariable Integer examSetQuestionId) throws UserException {
        examSetQuestionService.deleteExamSetQuestion(Long.valueOf(examSetQuestionId));
        return ResponseEntity.ok("Exam deleted Successfully");
    }
}
