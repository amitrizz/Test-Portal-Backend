package com.springsecurity.examaccess.service;

import com.springsecurity.examaccess.model.ExamAccess;
import com.springsecurity.user.exception.UserException;

import java.util.List;

public interface ExamAccessService {
    ExamAccess createExamAccess(ExamAccess examAccess) throws UserException;
    ExamAccess updateExamAccess(ExamAccess examAccess, Long examId) throws UserException;
    void deleteExamAccess(Long examAccessId) throws UserException;
    List<Integer> getAllExamsAccess(Long userId, boolean access);
    ExamAccess getExamAccessById(Long examAccessId) throws UserException;
    void removeExpiredExamAccess();

    void deleteExamAccessByExamId(Long examId);
}
