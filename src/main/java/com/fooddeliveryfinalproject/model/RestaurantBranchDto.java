package com.fooddeliveryfinalproject.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantBranchDto {

    private Long branchId;

    private MenuDto menuDto;

    private AddressDto addressDto;

    private String phoneNumber;
}
