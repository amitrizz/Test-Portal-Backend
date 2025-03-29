package com.springsecurity.examnames.repository;

import com.springsecurity.examnames.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam,Long> {
}
