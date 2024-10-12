package com.fooddeliveryfinalproject.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantBranchDto {

    private Long branchId;

    private RestaurantDto restaurantDto;

    private MenuDto menuDto;

    private AddressDto addressDto;

    @NotNull
    private String phoneNumber;
}
