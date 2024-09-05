package com.fooddeliveryfinalproject.model;


import lombok.Data;

@Data
public class RestaurantBranchDto {

    private Long branchId;

    private RestaurantDto restaurantDto;

    private MenuDto menuDto;

    private String phoneNumber;

    private AddressDto addressDto;
}
