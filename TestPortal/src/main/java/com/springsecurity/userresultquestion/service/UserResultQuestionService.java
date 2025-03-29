package com.springsecurity.userresultquestion.service;

import com.springsecurity.examsetquestion.model.Category;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.userresultquestion.dto.UserResultResponse;
import com.springsecurity.userresultquestion.model.UserResultQuestion;
import com.springsecurity.userresultquestion.repository.ExamSetCategoryProjection;
import com.springsecurity.userresultquestion.repository.UserRankingProjection;
import com.springsecurity.userresultquestion.repository.UserResultProjection;
import com.springsecurity.userresultquestion.repository.UserTakenExamSets;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserResultQuestionService {
    String createUserResultQuestions(List<UserResultQuestion> userResultQuestions,Long userId) throws UserException;
    void deleteUserResultQuestions(Long examSetId,Long userId) throws UserException;
    List<UserResultQuestion> getAllUserResultCategoryQuestions(Long examSetId, String category,Long userId);
    List<UserResultQuestion> getAllUserResultQuestion(Integer examSetId,Long userId);

    List<UserResultResponse> getExamSetResultRanks(Long examSetId);
    List<UserResultProjection> getExamSetResultDetails(Long examSetId, Long userId);
    UserRankingProjection getCurrentUserRank(Long examSetId, Long userId);
    List<ExamSetCategoryProjection> getExamSetCategories(Long examSetId);
    List<UserTakenExamSets> getUserGivenExamSets(Long userId);

}
