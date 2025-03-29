package com.springsecurity.payments.service;

import com.razorpay.RazorpayException;
import com.springsecurity.payments.model.Payment;
import com.springsecurity.user.exception.UserException;

import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment,String username) throws RazorpayException;
    boolean verifyPayment(String orderId, String paymentId, String signature) throws Exception;
    void deletePayment(Long paymentId) throws UserException;
    List<Payment> getPaymentsByUserId(Long userId);
    List<Payment> getPaymentsByExamId(Long examId);
    List<Payment> getAlPayments();
}
