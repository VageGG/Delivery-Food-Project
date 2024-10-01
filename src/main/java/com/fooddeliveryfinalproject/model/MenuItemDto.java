package com.fooddeliveryfinalproject.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {

    private Long menuItemId;

    private String name;

    private Double price;

    private String description;

}
