package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.User;
import lombok.Data;

import java.util.List;


@Data
public class DriverDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private User.Role role;

    private List<DeliveryDto> deliveriesDto;

}
