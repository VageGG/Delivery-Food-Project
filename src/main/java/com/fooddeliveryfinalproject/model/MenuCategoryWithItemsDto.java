package com.fooddeliveryfinalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryWithItemsDto {

    private Long categoryId;
    private String name;
    private List<MenuItemDto> items;

}