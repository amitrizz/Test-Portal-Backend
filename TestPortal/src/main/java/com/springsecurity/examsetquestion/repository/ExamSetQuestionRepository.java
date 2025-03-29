package com.springsecurity.examsetquestion.repository;

import com.springsecurity.examsetquestion.model.Category;
import com.springsecurity.examsetquestion.model.ExamSetQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamSetQuestionRepository extends JpaRepository<ExamSetQuestion,Long> {


    List<ExamSetQuestion> findByExamSetIdAndCategoryOrderByMultiQuestionValueAsc(Long examSet, String category);
    List<ExamSetQuestion> findByExamSetId(Long examSet);

    void deleteByExamSetId(Long examSetId);
}
