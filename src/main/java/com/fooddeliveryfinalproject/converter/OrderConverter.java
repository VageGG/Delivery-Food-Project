package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.OrderDto;

public class OrderConverter implements Converter<Order, OrderDto> {

    @Override
    public Order convertToEntity(OrderDto orderDto, Order order) {
        order.setOrderId(orderDto.getOrderId());
        order.setCustomer(orderDto.getCustomerDto());
        order.setItems(orderDto.getItemsDto());
        order.setDelivery(orderDto.getDeliveryDto());
        order.setStatus(orderDto.getStatus());
        return order;
    }

    @Override
    public OrderDto convertToModel(Order order, OrderDto orderDto) {
        orderDto.setOrderId(order.getOrderId());
        orderDto.setCustomerDto(order.getCustomer());
        orderDto.setItemsDto(order.getItems());
        orderDto.setDeliveryDto(order.getDelivery());
        orderDto.setStatus(order.getStatus());
        return orderDto;
    }
}
