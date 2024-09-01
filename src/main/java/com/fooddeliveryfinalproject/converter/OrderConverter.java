package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.OrderDto;

public class OrderConverter implements Converter<Order, OrderDto> {

    @Override
    public Order convertToEntity(OrderDto orderDto, Order order) {
        order.setOrderId(orderDto.getOrderId());
        order.setCustomer(orderDto.getCustomer());
        order.setItems(orderDto.getItems());
        order.setDelivery(orderDto.getDelivery());
        order.setStatus(orderDto.getStatus());
        return order;
    }

    @Override
    public OrderDto convertToModel(Order order, OrderDto orderDto) {
        orderDto.setOrderId(order.getOrderId());
        orderDto.setCustomer(order.getCustomer());
        orderDto.setItems(order.getItems());
        orderDto.setDelivery(order.getDelivery());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }
}
