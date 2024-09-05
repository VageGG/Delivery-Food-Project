package com.fooddeliveryfinalproject.model;


import lombok.Data;

import java.util.List;

@Data
public class MenuItemDto {

    private Long menuItemId;

    private MenuCategoryDto menuCategoryDto;

    private List<CartItemDto> cartDtos;

    private List<OrderItemDto> orderDtos;

    private String name;

    private Double price;

    private String description;

}
