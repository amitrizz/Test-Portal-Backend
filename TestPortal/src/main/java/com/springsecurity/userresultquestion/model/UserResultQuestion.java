package com.springsecurity.userresultquestion.model;


import com.springsecurity.examsetquestion.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_result_question")
public class UserResultQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionId;

    private Long userId;

    private Long examSetId;

    private String category;

    private String userAnswer;

    private Double questionMarks;

}
