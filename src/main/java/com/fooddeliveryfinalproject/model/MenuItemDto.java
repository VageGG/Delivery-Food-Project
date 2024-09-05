package com.fooddeliveryfinalproject.model;


import lombok.Data;

@Data
public class MenuItemDto {

    private Long menuItemId;

    private MenuCategoryDto menuCategoryDto;

    private CartDto orderCartDto;

    private String name;

    private Double price;

    private String description;

}
