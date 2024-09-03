package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepo repo;
    @Autowired
    private DateTimeService dateTimeService;

    @Transactional
    public Order createOrder(Order order) {
        String dateTime = dateTimeService.getFormattedCurrentDateTime();
        order.getDelivery().setDateTime(dateTime);
        return this.repo.save(order);
    }

    public Order getOrderById(long id) {
        Order order = this.repo.findById(id).get();
        if (order == null) {
            throw new RuntimeException("order not found");
        }
        
        return order;
    }

    @Transactional
    public Order updateOrder(Order order) {
        getOrderById(order.getOrderId()); //checking if order exists
        return this.repo.save(order);
    }

    @Transactional
    public void deleteOrder(long id) {
        Order order = getOrderById(id);
        this.repo.delete(order);
    }
}
