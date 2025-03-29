package com.springsecurity.examaccess.repository;

import com.springsecurity.examaccess.model.ExamAccess;
import com.springsecurity.user.exception.UserException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamAccessRepository extends JpaRepository<ExamAccess,Long> {
//    isAccessed
    List<ExamAccess> findAllByUserIdAndAccessed(Long userId,boolean access);
    ExamAccess findByPaymentId(String paymentId);
    List<ExamAccess> findAllByAccessed(boolean accessed);
    void deleteByExamId(Long examId);
}
