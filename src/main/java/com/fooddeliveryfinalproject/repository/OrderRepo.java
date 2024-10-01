package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    Order findByDelivery(Delivery delivery);
    List<Order> findByStatus(Order.OrderStatus orderStatus);
}
