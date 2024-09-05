package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.CustomerAddress;
import lombok.Data;

import java.util.List;

@Data
public class AddressDto {

    private Long id;

    private String country;

    private String city;

    private String state;

    private String street;

    private String houseNumber;

    private String apartmentNumber;

    private List<CustomerAddressDto> customerAddressDtoList;

    private RestaurantBranchDto restaurantBranchDto;

    private List<DeliveryDto> pickupLocationDto;

    private List<DeliveryDto> dropoffLocationDto;
}
