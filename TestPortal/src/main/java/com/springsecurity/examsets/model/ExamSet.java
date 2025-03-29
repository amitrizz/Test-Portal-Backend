package com.springsecurity.examsets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ExamSet")
public class ExamSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long setId;

    private String examSetName;

    private Long examId;
    private LocalDateTime createdAt;
    private Boolean isAttempted=false;

}
