package com.springsecurity.examnames.controller;

import com.springsecurity.examnames.model.Exam;
import com.springsecurity.examnames.service.ExamService;
import com.springsecurity.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/exam")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/get/all-exams")
    public ResponseEntity<List<Exam>> getAllUser() {
        return ResponseEntity.ok(examService.getAllExams());
    }

    @GetMapping("/get/by-id/{examId}")
    public ResponseEntity<Exam> getExamById(@PathVariable Integer examId) throws UserException {
        return ResponseEntity.ok(examService.getExamById(Long.valueOf(examId)));
    }

    @PostMapping("/create-exam")
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) throws UserException {
        return ResponseEntity.ok(examService.createExam(exam));
    }

    @PutMapping("/update-exam/{examId}")
    public ResponseEntity<Exam> updateExam(@RequestBody Exam exam,@PathVariable Integer examId) throws UserException {
        return ResponseEntity.ok(examService.updateExam(exam, Long.valueOf(examId)));
    }

    @DeleteMapping("/delete-exam/{examId}")
    public ResponseEntity<String> deleteExam(@PathVariable Integer examId) throws UserException {
        examService.deleteExam(Long.valueOf(examId));
        return ResponseEntity.ok("Exam deleted Successfully");
    }
}
