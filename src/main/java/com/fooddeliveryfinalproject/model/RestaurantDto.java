package com.fooddeliveryfinalproject.model;


import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    private Long restId;

    private String restName;

    private RestaurantManager restaurantManager;

    private List<RestaurantBranchDto> branchesDto;

    private double rating;
}
