package com.example.homestay.controller;

import com.example.homestay.entity.Payment;
import com.example.homestay.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/create")
    public String createPayment(@RequestBody Payment payment) {
        payment.setStatus("PAID");
        payment.setPaymentTime(LocalDateTime.now());
        paymentRepository.save(payment);
        return "Payment recorded successfully!";
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
