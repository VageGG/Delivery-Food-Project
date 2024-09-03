package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.*;
import lombok.*;

@Data
public class OrderDto {

    private Long orderId;

    private CustomerDto customerDto;

    private OrderCartDto orderCartDto;

    private DeliveryDto deliveryDto;

    private Order.OrderStatus status;
}
