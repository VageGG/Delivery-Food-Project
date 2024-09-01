package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
