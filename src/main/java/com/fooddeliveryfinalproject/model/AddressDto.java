package com.fooddeliveryfinalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    private String country;

    private String city;

    private String state;

    private String street;

    private String houseNumber;

    private String apartmentNumber;
}
