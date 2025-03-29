package com.springsecurity.userresultquestion.controller;


import com.springsecurity.admin.service.UserService;
import com.springsecurity.auth.security.jwt.JwtUtils;
import com.springsecurity.examsetquestion.model.Category;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import com.springsecurity.user.exception.UserException;
import com.springsecurity.user.model.User;
import com.springsecurity.userresultquestion.dto.UserResultResponse;
import com.springsecurity.userresultquestion.model.UserResultQuestion;
import com.springsecurity.userresultquestion.repository.ExamSetCategoryProjection;
import com.springsecurity.userresultquestion.repository.UserRankingProjection;
import com.springsecurity.userresultquestion.repository.UserResultProjection;
import com.springsecurity.userresultquestion.repository.UserTakenExamSets;
import com.springsecurity.userresultquestion.service.UserResultQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/result")
public class UserResultQuestionController {
    private final UserResultQuestionService userResultQuestionService;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public UserResultQuestionController(UserResultQuestionService userResultQuestionService, JwtUtils jwtUtils, UserService userService) {
        this.userResultQuestionService = userResultQuestionService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @GetMapping("/set/{examSetId}")
    public ResponseEntity<List<UserResultQuestion>> getAllUserResultQuestion(@PathVariable Integer examSetId,@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.getAllUserResultQuestion(examSetId, Long.valueOf(user.getId())));
    }

    @GetMapping("/category/{examSetId}/{category}")
    public ResponseEntity<List<UserResultQuestion>> getAllUserResultQuestionCategoryWise(@PathVariable Integer examSetId,@PathVariable String category,@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.getAllUserResultCategoryQuestions(Long.valueOf(examSetId),category, Long.valueOf(user.getId())));
    }

    @GetMapping("/set/ranks/{examSetId}")
    public ResponseEntity<List<UserResultResponse>> getUserExamSetRanks(@PathVariable Integer examSetId, @RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.getExamSetResultRanks(Long.valueOf(examSetId)));
    }

    @GetMapping("/user/rank/{examSetId}")
    public ResponseEntity<UserRankingProjection> getUserCurrentRanks(@PathVariable Integer examSetId,@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.getCurrentUserRank(Long.valueOf(examSetId), Long.valueOf(user.getId())));
    }

    @GetMapping("/set/details/{examSetId}")
    public ResponseEntity<List<UserResultProjection>> getUserExamSetDetails(@PathVariable Integer examSetId, @RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.getExamSetResultDetails(Long.valueOf(examSetId), Long.valueOf(user.getId())));
    }

    @GetMapping("/given/sets")
    public ResponseEntity<List<UserTakenExamSets>> getUserGivenSets(@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.getUserGivenExamSets(Long.valueOf(user.getId())));
    }

    @GetMapping("/view-analysis/{examSetId}")
    public ResponseEntity<List<ExamSetCategoryProjection>> getExamSetCategories(@PathVariable Integer examSetId, @RequestHeader("Authorization") String authToken) throws UserException {
        return ResponseEntity.ok(userResultQuestionService.getExamSetCategories(Long.valueOf(examSetId)));
    }

    @PostMapping("/create-question")
    public ResponseEntity<String> createUserResultQuestions(@RequestBody List<UserResultQuestion> userResultQuestions,@RequestHeader("Authorization") String authToken) throws UserException {
        String token = authToken.substring(7);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getUserDetails(username);
        return ResponseEntity.ok(userResultQuestionService.createUserResultQuestions(userResultQuestions, Long.valueOf(user.getId())));
    }
}
