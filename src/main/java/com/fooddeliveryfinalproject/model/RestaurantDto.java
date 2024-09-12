package com.fooddeliveryfinalproject.model;


import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {

    private Long restId;

    private String name;

    private RestaurantManagerDto restaurantManagerDto;

    private List<RestaurantBranchDto> branchesDto;

    private List<ReviewDto> reviewDtos;
}
