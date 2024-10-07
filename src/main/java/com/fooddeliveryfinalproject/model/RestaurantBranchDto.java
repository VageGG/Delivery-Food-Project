package com.fooddeliveryfinalproject.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantBranchDto {

    private Long branchId;

    private RestaurantDto restaurantDto;

    private MenuDto menuDto;

    private AddressDto addressDto;

    @NotNull
    private String phoneNumber;
}
