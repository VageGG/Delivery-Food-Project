package com.fooddeliveryfinalproject.model;


import lombok.Data;

import java.util.List;

@Data
public class MenuItemDto {

    private Long menuItemId;

    private String name;

    private Double price;

    private String description;

}
