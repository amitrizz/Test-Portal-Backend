package com.springsecurity.examsets.service;

import com.springsecurity.examsets.model.ExamSet;
import com.springsecurity.user.exception.UserException;

import java.util.List;

public interface ExamSetService {

    ExamSet createExamSet(ExamSet examSet) throws UserException;
    ExamSet updateExamSet(ExamSet examSet, Long examSetId) throws UserException;
    List<ExamSet> getAllExamSetByExamId(Long examId);
    Boolean isExamSetExist(Long examSetId);
    void deleteExamSet(Long examSetId) throws UserException;
    void deleteExamSetsByExamId(Long examId) throws UserException;
    ExamSet getExamSetById(Long examSetId) throws UserException;
}
