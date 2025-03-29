package com.springsecurity.examnames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


//@ElementCollection
//@CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "exam_set_question_id")) // Matches the ID of ExamSetQuestion
//private List<String> options;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ExamNames")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long examId;

    private String examName;

    @ElementCollection
    @CollectionTable(name = "exam_category", joinColumns = @JoinColumn(name = "ExamNames"))
    private Map<String,Integer> examCategory;

    private Long price;

}
