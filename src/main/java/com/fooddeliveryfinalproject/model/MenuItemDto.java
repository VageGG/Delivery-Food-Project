package com.fooddeliveryfinalproject.model;


import com.fooddeliveryfinalproject.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemDto {

    private Long menuItemId;

    private Menu menu;

    private String name;

    private Double price;

    private String description;

}
