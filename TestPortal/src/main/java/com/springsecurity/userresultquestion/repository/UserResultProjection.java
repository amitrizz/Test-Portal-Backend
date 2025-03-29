package com.springsecurity.userresultquestion.repository;

public interface UserResultProjection {
    String getCategory();
    Integer getTotalQuestion();
    Integer getCorrectAns();
    Double getTotalMarks();
}

