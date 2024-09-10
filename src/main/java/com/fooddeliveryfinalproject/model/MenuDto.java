package com.fooddeliveryfinalproject.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private Long id;

    private RestaurantBranchDto restaurantBranchDto;

    private List<MenuCategoryDto> menuCategoriesDto;
}
