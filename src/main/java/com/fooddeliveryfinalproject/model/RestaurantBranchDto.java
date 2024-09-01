package com.fooddeliveryfinalproject.model;


import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantBranchDto {

    private Long branchId;

    private Restaurant restaurant;

    private Menu menu;

    private String phoneNumber;

    private String location;


}
