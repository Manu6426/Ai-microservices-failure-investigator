package com.manoj.aiincident.payment.controller;

import com.manoj.aiincident.payment.dto.PaymentRequest;
import com.manoj.aiincident.payment.model.Payment;
import com.manoj.aiincident.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "service", "payment-service",
                "status", "UP"
        );
    }

    @PostMapping
    public Payment processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}