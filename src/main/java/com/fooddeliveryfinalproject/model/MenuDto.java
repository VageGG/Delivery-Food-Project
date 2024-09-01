package com.fooddeliveryfinalproject.model;

import lombok.Data;
@Data
public class MenuDto {

    private Long id;

    private RestaurantBranchDto restaurantBranchDto;

  //  private Set<MenuItem> menuItems;

    private MenuCategoryDto menuCategoryDto;
}
