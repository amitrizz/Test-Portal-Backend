package com.springsecurity.examsetquestion.model;

import com.springsecurity.user.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exam_set_question")
//here all thing are related to each Question
public class ExamSetQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String answer;

    private Long examSetId;

    private String category;

    @Column(length = 1000)
    private String question;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "exam_set_question_id")) // Matches the ID of ExamSetQuestion
    private List<String> options;

    private  boolean info = false;

    //text before image
    @Column(length = 1500)
    private String beforeImage;

    //image url
    private String imageUrl;

    // text after image
    @Column(length = 1500)
    private String afterImage;

    // used when DI / RC / Pz are used for Data
    private boolean multiQuestion=false;
    private Integer multiQuestionValue=0;

}
