package com.springsecurity.payments.service.imp;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.springsecurity.examaccess.model.ExamAccess;
import com.springsecurity.examaccess.repository.ExamAccessRepository;
import com.springsecurity.payments.model.Payment;
import com.springsecurity.payments.repository.PaymentRepository;
import com.springsecurity.payments.service.PaymentService;
import com.springsecurity.user.exception.UserException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImp implements PaymentService{
    @Value("${razorpay.api.key}")
    private String key;

    @Value("${razorpay.api.secret}")
    private String secret;

    private RazorpayClient razorpayClient;
    private PaymentRepository paymentRepository;
    private final ExamAccessRepository examAccessRepository;

    public PaymentServiceImp(PaymentRepository paymentRepository, ExamAccessRepository examAccessRepository) throws RazorpayException {
        this.paymentRepository = paymentRepository;
        this.examAccessRepository = examAccessRepository;
    }

    @Override
    public Payment createPayment(Payment payment, String username) throws RazorpayException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount",payment.getAmount()*100);
        jsonObject.put("currency","INR");
        jsonObject.put("receipt",username);
        razorpayClient = new RazorpayClient(key, secret);

        Order razorpayOrder = razorpayClient.orders.create(jsonObject);

        if (razorpayOrder!=null){
            payment.setRazorpayOrderId(razorpayOrder.get("id"));
            payment.setOrderStatus(razorpayOrder.get("status"));
        }
        payment.setCreateAt(LocalDateTime.now());
        Payment payment1 = paymentRepository.save(payment);
        System.out.println(payment1);
        return payment1;
    }

    @Override
    public boolean verifyPayment(String orderId, String paymentId, String signature) throws Exception {
        Payment payment = paymentRepository.findByRazorpayOrderId(orderId);
        if (payment == null) {
            throw new RuntimeException("Order not found in database.");
        }

        JSONObject attributes = new JSONObject();
        attributes.put("razorpay_order_id", orderId);
        attributes.put("razorpay_payment_id", paymentId);
        attributes.put("razorpay_signature", signature);

        boolean isValid = Utils.verifyPaymentSignature(attributes, secret);
        if (isValid){
            ExamAccess examAccess = new ExamAccess();
            examAccess.setPaymentId(paymentId);
            examAccess.setExamId(payment.getExamId());
            examAccess.setCreateAt(LocalDateTime.now());
            examAccess.setUserId(payment.getUserId());
            examAccessRepository.save(examAccess);

        }

        payment.setRazorpayPaymentId(paymentId);
        payment.setRazorpaySignature(signature);
        payment.setOrderStatus(isValid ? "SUCCESS" : "FAILED");
        paymentRepository.save(payment);


        return isValid;
    }

    @Override
    public void deletePayment(Long paymentId) throws UserException {
        Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment==null){
            throw new UserException("Payment not found with this paymentId");
        }
        paymentRepository.deleteById(paymentId);
        return;
    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findAllByUserId(userId);
    }

    @Override
    public List<Payment> getPaymentsByExamId(Long examId) {
        return paymentRepository.findAllByExamId(examId);
    }

    @Override
    public List<Payment> getAlPayments() {
        return paymentRepository.findAll();
    }
}
