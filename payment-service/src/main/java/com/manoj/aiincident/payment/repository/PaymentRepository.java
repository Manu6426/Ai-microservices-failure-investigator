package com.manoj.aiincident.payment.repository;

import com.manoj.aiincident.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}