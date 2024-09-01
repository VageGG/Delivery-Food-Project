package com.fooddeliveryfinalproject.model;

import lombok.*;

import java.util.List;

@Data
public class OrderCartDto {

    private Long cartId;

    private OrderDto orderDto;

    private List<MenuItemDto> itemsDto;

    private Integer count;
}
