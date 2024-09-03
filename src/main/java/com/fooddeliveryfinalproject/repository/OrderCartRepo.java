package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartRepo extends JpaRepository<OrderCart, Long> {
}
