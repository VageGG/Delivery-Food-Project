package com.fooddeliveryfinalproject.model;

import lombok.Data;

@Data
public class RestaurantManagerDto {

    private Long id;

    private String username;

    private String email;

    private String phoneNumber;

    private RestaurantDto restaurant;
}
