package com.fooddeliveryfinalproject.model;

import lombok.Data;

@Data
public class OrderItemDto {

    private Long orderId;

    private Long menuItemId;

    private Integer quantity;
}
