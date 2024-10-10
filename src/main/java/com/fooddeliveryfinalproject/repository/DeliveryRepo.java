package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, Long> {
    Delivery findByTrackingNumber(String trackingNumber);
    List<Delivery> findByDriverId(Long driverId);
    Delivery findByOrder(Order order);
}
