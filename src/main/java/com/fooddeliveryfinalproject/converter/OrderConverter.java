package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter implements Converter<Order, OrderDto> {

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
