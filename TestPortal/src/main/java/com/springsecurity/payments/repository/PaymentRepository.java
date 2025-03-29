package com.springsecurity.payments.repository;

import com.springsecurity.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findAllByUserId(Long userId);
    List<Payment> findAllByExamId(Long examId);
    Payment findByRazorpayOrderId(String orderId);
}
