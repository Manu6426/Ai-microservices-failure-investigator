package com.manoj.aiincident.payment.service;

import com.manoj.aiincident.payment.client.InventoryClient;
import com.manoj.aiincident.payment.dto.PaymentRequest;
import com.manoj.aiincident.payment.model.Payment;
import com.manoj.aiincident.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InventoryClient inventoryClient;

    public PaymentService(
            PaymentRepository paymentRepository,
            InventoryClient inventoryClient) {

        this.paymentRepository = paymentRepository;
        this.inventoryClient = inventoryClient;
    }

    public Payment processPayment(PaymentRequest request) {

        inventoryClient.reserveStock(
                request.getProductCode(),
                request.getQuantity()
        );

        Payment payment = new Payment();

        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus("SUCCESS");
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}