package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.OrderCart;
import com.fooddeliveryfinalproject.repository.OrderCartRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCartService {
    @Autowired
    private OrderCartRepo repo;

    @Transactional
    public OrderCart createOrderCart(OrderCart orderCart) {
        return this.repo.save(orderCart);
    }

    public OrderCart getOrderCartById(long id) {
        OrderCart orderCart = this.repo.findById(id).get();
        if (orderCart == null) {
            throw new RuntimeException("order cart not found");
        }

        return orderCart;
    }

    @Transactional
    public OrderCart updateOrderCart(OrderCart orderCart) {
        getOrderCartById(orderCart.getCartId()); //checking if orderCart exists
        return this.repo.save(orderCart);
    }

    @Transactional
    public void deleteOrderCart(long id) {
        OrderCart orderCart = getOrderCartById(id);
        this.repo.delete(orderCart);
    }
}
