package com.fooddeliveryfinalproject.model;

import lombok.*;

import java.util.List;

@Data
public class MenuCategoryDto {

    private Long categoryId;

    private String name;

    private List<MenuItemDto> itemsDto;

    private MenuDto menuDto;

}
