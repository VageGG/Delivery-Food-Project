package com.fooddeliveryfinalproject.model;

import lombok.*;

@Data
public class ReviewDto {

    private Long reviewId;

    private CustomerDto customerDto;

    private RestaurantDto restaurantDto;

    private DriverDto driverDto;

    private Byte rating;

    private String comment;
}
