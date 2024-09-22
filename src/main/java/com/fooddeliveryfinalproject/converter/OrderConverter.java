package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderConverter implements Converter<Order, OrderDto> {

    private CustomerConverter customerConverter;

    @Autowired
    @Lazy
    private OrderItemConverter orderItemConverter;

    private final DeliveryConverter deliveryConverter;

    public OrderConverter(DeliveryConverter deliveryConverter) {
        this.deliveryConverter = deliveryConverter;
    }

    @Autowired
    @Lazy
    public void setCustomerConverter(CustomerConverter customerConverter) {
        this.customerConverter = customerConverter;
    }

    @Override
    public Order convertToEntity(OrderDto model, Order entity) {
        entity.setOrderId(model.getOrderId());
        entity.setStatus(model.getStatus());
        return entity;
    }

    @Override
    public OrderDto convertToModel(Order entity, OrderDto model) {
        model.setOrderId(entity.getOrderId());
        model.setStatus(entity.getStatus());
        return model;
    }
}
