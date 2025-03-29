package com.springsecurity.examaccess.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exam_access")
public class ExamAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accessId;

    private Long examId;

    private Long userId;
    private String paymentId;

    private Boolean accessed=true;

    private LocalDateTime createAt;
}
