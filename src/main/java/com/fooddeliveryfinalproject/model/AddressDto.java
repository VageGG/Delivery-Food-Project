package com.fooddeliveryfinalproject.model;

import lombok.Data;

@Data
public class AddressDto {

    private Long id;

    private String country;

    private String city;

    private String state;

    private String street;

    private String houseNumber;

    private String apartmentNumber;

}
