package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.OrderItem;
import com.fooddeliveryfinalproject.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepo repo;
    @Autowired
    private DateTimeService dateTimeService;

    @Transactional
    public Order createOrder(Order order) {
        LocalDateTime dateTime = LocalDateTime.now();
        order.getDelivery().setOrderTime(dateTime);
        return this.repo.save(order);
    }

    public Order getOrderById(long id) {
        Order order = this.repo.findById(id).get();
        if (order == null) {
            throw new RuntimeException("order not found");
        }
        
        return order;
    }

    public Double calculateTotal(long orderId) {
        List<OrderItem> items = this.repo.findById(orderId).get().getItems();

        if (items.size() == 0) {
            throw new RuntimeException("cart is empty");
        }

        double total = 0;
        for (OrderItem orderItem: items) {
            total+= total + orderItem.getMenuItem().getPrice();
        }

        return total;
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
