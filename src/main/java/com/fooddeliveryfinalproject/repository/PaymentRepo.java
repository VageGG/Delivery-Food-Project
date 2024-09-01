package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}