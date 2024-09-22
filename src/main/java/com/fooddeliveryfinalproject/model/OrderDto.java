package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.*;
import lombok.*;

import java.util.List;

@Data
public class OrderDto {

    private Long orderId;

    private Order.OrderStatus status;
}
