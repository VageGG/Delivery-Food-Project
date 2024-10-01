package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;

    private Order.OrderStatus status;
}
