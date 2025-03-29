package com.springsecurity.examnames.service;

import com.springsecurity.examnames.model.Exam;
import com.springsecurity.user.exception.UserException;

import java.util.List;

public interface ExamService {
    Exam createExam(Exam exam) throws UserException;
    Exam updateExam(Exam exam, Long examId) throws UserException;
    void deleteExam(Long examId) throws UserException;
    List<Exam> getAllExams();
    Exam getExamById(Long examId) throws UserException;
    Boolean isExamExist(Long examId);
}
