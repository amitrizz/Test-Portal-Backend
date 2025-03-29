package com.springsecurity.userresultquestion.service.imp;

import com.springsecurity.admin.service.UserService;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.examsetquestion.repository.ExamSetQuestionRepository;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.userresultquestion.dto.UserResultResponse;
import com.springsecurity.userresultquestion.model.UserResultQuestion;
import com.springsecurity.userresultquestion.repository.*;
import com.springsecurity.userresultquestion.service.UserResultQuestionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserResultQuestionServiceImp implements UserResultQuestionService {
    private final UserResultQuestionRepository userResultRepository;
    private final ExamSetQuestionRepository examSetQuestionRepository;
    private final UserService userService;

    public UserResultQuestionServiceImp(UserResultQuestionRepository userResultRepository, ExamSetQuestionRepository examSetQuestionRepository, UserService userService) {
        this.userResultRepository = userResultRepository;
        this.examSetQuestionRepository = examSetQuestionRepository;
        this.userService = userService;
    }

    @Override
    public String createUserResultQuestions(List<UserResultQuestion> userResultQuestions,Long userId) throws UserException {
        for (UserResultQuestion userResultQuestion:userResultQuestions){
            Long questionId = userResultQuestion.getQuestionId();
            UserResultQuestion userResultQuestion1 = userResultRepository.findByUserIdAndQuestionId(userId,questionId);
            if(userResultQuestion1==null){
                userResultQuestion1 = new UserResultQuestion();
                userResultQuestion1.setQuestionId(userResultQuestion.getQuestionId());
                userResultQuestion1.setCategory(userResultQuestion.getCategory());
                userResultQuestion1.setUserId(userId);
                userResultQuestion1.setUserAnswer(userResultQuestion.getUserAnswer());
                userResultQuestion1.setExamSetId(userResultQuestion.getExamSetId());
                Optional<ExamSetQuestion> examSetQuestion = examSetQuestionRepository.findById(userResultQuestion.getQuestionId());

                if (examSetQuestion.isPresent()){
                    ExamSetQuestion res = examSetQuestion.get();
                    if(res.getAnswer().trim().equals(userResultQuestion.getUserAnswer().trim())) {
                        userResultQuestion1.setQuestionMarks(1.0);
                    }else if(userResultQuestion.getUserAnswer().isEmpty()){
                        userResultQuestion1.setQuestionMarks(0.0);
                    }else{
                        userResultQuestion1.setQuestionMarks(-0.25);
                    }

                }
                userResultRepository.save(userResultQuestion1);
            }
        }
        return "Data Submitted";
    }

    @Transactional
    @Override
    public void deleteUserResultQuestions(Long examSetId, Long userId) {
        userResultRepository.deleteByExamSetIdAndUserId(examSetId,userId);
    }

    @Override
    public List<UserResultQuestion> getAllUserResultCategoryQuestions(Long examSetId, String category, Long userId) {
        return userResultRepository.findByUserIdAndCategoryAndExamSetId(userId,category,examSetId);
    }

    @Override
    public List<UserResultQuestion> getAllUserResultQuestion(Integer examSetId, Long userId) {
        return userResultRepository.findByUserIdAndExamSetId(userId, Long.valueOf(examSetId));
    }


    @Override
    public List<UserResultResponse> getExamSetResultRanks(Long examSetId) {
        List<UserResultResponse> userResultResponses = new java.util.ArrayList<>(Collections.singletonList(new UserResultResponse()));
        List<UserRankingProjection> userRankingProjections = userResultRepository.getUserRankings(examSetId);
        for (UserRankingProjection userRankingProjection:userRankingProjections){

            UserResultResponse userResultResponse=new UserResultResponse();
            userResultResponse.setUserRankingProjection(userRankingProjection);
            Optional<User> checkUser = userService.getUserByUserId(Math.toIntExact(userRankingProjection.getUserId()));
            checkUser.ifPresent(user -> userResultResponse.setUserName(user.getFirstName() + " " + user.getLastName()));

            userResultResponses.add(userResultResponse);
        }
        return userResultResponses;
    }

    @Override
    public List<UserResultProjection> getExamSetResultDetails(Long examSetId, Long userId) {

        return userResultRepository.getUserResults(examSetId,userId);
    }

    @Override
    public UserRankingProjection getCurrentUserRank(Long examSetId, Long userId) {
        Optional<UserRankingProjection> userRankingProjection = userResultRepository.getCurrentUserRank(examSetId,userId);
        return userRankingProjection.orElse(null);
    }

    @Override
    public List<ExamSetCategoryProjection> getExamSetCategories(Long examSetId) {
        return userResultRepository.getExamSetCategory(examSetId);
    }

    @Override
    public List<UserTakenExamSets> getUserGivenExamSets(Long userId) {
        return userResultRepository.getCurrentUserExamSets(userId);
    }
}
