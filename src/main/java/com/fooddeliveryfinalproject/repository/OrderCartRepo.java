package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCartRepo extends JpaRepository<OrderCart, Long> {
}
