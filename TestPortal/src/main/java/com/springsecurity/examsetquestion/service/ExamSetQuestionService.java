package com.springsecurity.examsetquestion.service;

import com.springsecurity.examsetquestion.model.Category;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.user.exception.UserException;

import java.util.List;

public interface ExamSetQuestionService {
    ExamSetQuestion createExamSetQuestion(ExamSetQuestion examSetQuestion) throws UserException;
    ExamSetQuestion updateExamSetQuestion(ExamSetQuestion examSetQuestion, Long examSetQuestionId) throws UserException;
    void deleteExamSetQuestion(Long examSetQuestionId) throws UserException;
    List<ExamSetQuestion> getAllExamSetCategoryQuestion(Long examSetId, String category);
    ExamSetQuestion getExamSetQuestionById(Long examSetQuestionId) throws UserException;
    Boolean isExamSetQuestionExist(Long examSetQuestionId);
    List<ExamSetQuestion> getAllExamSetQuestions(Integer examSetId);

}
