package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter implements Converter<Order, OrderDto> {

    private CustomerConverter customerConverter;

    private final OrderCartConverter orderCartConverter;

    private final DeliveryConverter deliveryConverter;

    public OrderConverter(OrderCartConverter orderCartConverter,
                          DeliveryConverter deliveryConverter) {
        this.orderCartConverter = orderCartConverter;
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

        if (model.getCustomerDto() != null) {
            entity.setCustomer(customerConverter.convertToEntity(model.getCustomerDto(), new Customer()));
        }

        if (model.getOrderCartDto() != null) {
            entity.setOrderCart(orderCartConverter.convertToEntity(model.getOrderCartDto(), new OrderCart()));
        }

        if (model.getDeliveryDto() != null) {
            entity.setDelivery(deliveryConverter.convertToEntity(model.getDeliveryDto(), new Delivery()));
        }

        entity.setStatus(model.getStatus());
        return entity;
    }

    @Override
    public OrderDto convertToModel(Order entity, OrderDto model) {
        model.setOrderId(entity.getOrderId());

        if (entity.getCustomer() != null) {
            model.setCustomerDto(customerConverter.convertToModel(entity.getCustomer(), new CustomerDto()));
        }

        if (entity.getOrderCart() != null) {
            model.setOrderCartDto(orderCartConverter.convertToModel(entity.getOrderCart(), new OrderCartDto()));
        }

        if (entity.getDelivery() != null) {
            model.setDeliveryDto(deliveryConverter.convertToModel(entity.getDelivery(), new DeliveryDto()));
        }

        model.setStatus(entity.getStatus());
        return model;
    }
}
