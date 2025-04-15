package com.example.homestay.controller;

import com.example.homestay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestParam Long bookingId) {
        try {
            Map<String, Object> response = paymentService.createPayment(bookingId);
            if ("0".equals(response.get("resultCode").toString())) {
                return ResponseEntity.ok(response.get("payUrl"));
            } else {
                return ResponseEntity.badRequest().body(response.get("message"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/return")
    public ResponseEntity<?> handleReturn(@RequestParam Map<String, String> params) {
        String resultCode = params.get("resultCode");
        if ("0".equals(resultCode)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.badRequest().body("Thanh toán thất bại: " + params.get("message"));
        }
    }

    @PostMapping("/notify")
    public ResponseEntity<?> handleNotify(@RequestBody Map<String, Object> payload) {
        try {
            paymentService.handleNotify(payload);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}