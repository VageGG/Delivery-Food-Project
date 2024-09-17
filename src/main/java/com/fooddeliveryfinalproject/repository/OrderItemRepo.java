package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.OrderItem;
import com.fooddeliveryfinalproject.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, OrderItemId> {
}
