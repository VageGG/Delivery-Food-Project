package com.fooddeliveryfinalproject.model;


import lombok.Data;

@Data
public class MenuItemDto {

    private Long menuItemId;

    private MenuDto menuDto;

    private String name;

    private Double price;

    private String description;

}
