package com.springsecurity.examsets.controller;

import com.springsecurity.examsets.model.ExamSet;
import com.springsecurity.examsets.service.ExamSetService;
import com.springsecurity.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/exam/set")
public class ExamSetController {
    private final ExamSetService examSetService;

    public ExamSetController(ExamSetService examSetService) {
        this.examSetService = examSetService;
    }

    @GetMapping("/exam-sets/{examId}")
    public ResponseEntity<List<ExamSet>> getAllUser(@PathVariable Integer examId) {
        return ResponseEntity.ok(examSetService.getAllExamSetByExamId(Long.valueOf(examId)));
    }

    @GetMapping("/exam-set/{examSetId}")
    public ResponseEntity<ExamSet> getExamById(@PathVariable Integer examSetId) throws UserException {
        return ResponseEntity.ok(examSetService.getExamSetById(Long.valueOf(examSetId)));
    }

    @PostMapping("/exam-set")
    public ResponseEntity<ExamSet> createExam(@RequestBody ExamSet examSet) throws UserException {
        return ResponseEntity.ok(examSetService.createExamSet(examSet));
    }

    @PutMapping("/exam-set/{examSetId}")
    public ResponseEntity<ExamSet> updateExam(@RequestBody ExamSet examSet,@PathVariable Integer examSetId) throws UserException {
        return ResponseEntity.ok(examSetService.updateExamSet(examSet, Long.valueOf(examSetId)));
    }

    @DeleteMapping("/delete-exam/{examSetId}")
    public ResponseEntity<String> deleteExam(@PathVariable Integer examSetId) throws UserException {
        examSetService.deleteExamSet(Long.valueOf(examSetId));
        return ResponseEntity.ok("Exam deleted Successfully");
    }


}
