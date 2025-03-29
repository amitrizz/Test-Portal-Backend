package com.springsecurity.examsets.repository;

import com.springsecurity.examsets.model.ExamSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamSetRepository extends JpaRepository<ExamSet,Long> {
    List<ExamSet> findByExamId(Long examId);

}
