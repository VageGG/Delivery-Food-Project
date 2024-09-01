package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {

    private String menuId;

    private RestaurantBranch restaurantBranch;

  //  private Set<MenuItem> menuItems;

    private MenuCategory menuCategory;
}
