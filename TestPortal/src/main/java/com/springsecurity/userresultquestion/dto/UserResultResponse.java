package com.springsecurity.userresultquestion.dto;

import com.springsecurity.userresultquestion.repository.UserRankingProjection;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResultResponse {
    String userName;
    UserRankingProjection userRankingProjection;
}
