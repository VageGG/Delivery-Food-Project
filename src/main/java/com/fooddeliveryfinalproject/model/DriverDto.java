package com.fooddeliveryfinalproject.model;

import lombok.Data;

import java.util.List;


@Data
public class DriverDto {

    private Long id;

    private String username;

    private String email;

    private String phoneNumber;

    private List<DeliveryDto> deliveriesDto;

}
