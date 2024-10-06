package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    @NotNull
    private String country;

    @NotNull
    private String city;

    private String state;

    @NotNull
    private String street;

    private String houseNumber;

    private String apartmentNumber;
}
