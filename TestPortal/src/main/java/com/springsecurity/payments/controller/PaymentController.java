package com.springsecurity.payments.controller;


import com.razorpay.RazorpayException;
import com.springsecurity.auth.security.jwt.JwtUtils;
import com.springsecurity.payments.model.Payment;
// import com.springsecurity.payments.service.PaymentService;
import com.springsecurity.payments.service.PaymentService;
import com.springsecurity.user.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
     private final PaymentService paymentService;
     private final JwtUtils jwtUtils;

     public PaymentController(PaymentService paymentService,JwtUtils jwtUtils) {
         this.paymentService = paymentService;
         this.jwtUtils = jwtUtils;
     }

    @PostMapping("/create-order")
    public ResponseEntity<Map<String,String>> createPaymentOrder(@RequestBody Payment payment, @RequestHeader("Authorization") String authToken) throws RazorpayException {
        String token = authToken.substring(7);
         String username = jwtUtils.extractUsername(token);

         System.out.println(payment);

        Payment savedPayment = paymentService.createPayment(payment, username);
        Map<String, String> response = new HashMap<>();
        response.put("orderId", savedPayment.getRazorpayOrderId());
        response.put("status", savedPayment.getOrderStatus());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<Map<String,Object>> verifyPayment(@RequestBody Map<String, String> data) throws Exception {
        boolean isValid = paymentService.verifyPayment(
                data.get("razorpay_order_id"),
                data.get("razorpay_payment_id"),
                data.get("razorpay_signature")
        );

        Map<String, Object> response = new HashMap<>();
        response.put("isValid", isValid);
        response.put("message", isValid ? "Payment Successful" : "Payment Failed");

        return ResponseEntity.ok(response);
    }
     @DeleteMapping("/delete-payment")
     public ResponseEntity<String> deletePayment(@RequestParam Long paymentId) throws UserException {
         paymentService.deletePayment(paymentId);
         return ResponseEntity.ok("Deleted Successfully");
     }

     @GetMapping("/exam/{examId}")
     public ResponseEntity<List<Payment>> getPaymentByExamId(@PathVariable Integer examId){
         return ResponseEntity.ok(paymentService.getPaymentsByExamId(Long.valueOf(examId)));
     }

     @GetMapping("/exam/{userId}")
     public ResponseEntity<List<Payment>> getPaymentByUserId(@PathVariable Integer userId){
         return ResponseEntity.ok(paymentService.getPaymentsByUserId(Long.valueOf(userId)));
     }

     @GetMapping("/exam")
     public ResponseEntity<List<Payment>> getPayments(){
         return ResponseEntity.ok(paymentService.getAlPayments());
     }
}
