package com.fooddeliveryfinalproject.model;

import lombok.*;

import java.util.List;

@Data
public class CartDto {

    private Long cartId;

    private List<CartItemDto> itemsDto;

    private Integer count;
}
