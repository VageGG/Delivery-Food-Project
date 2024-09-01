package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
public class DriverDto {

    private Long id;

    private String username;

    private String email;

    private String phoneNumber;


    private List<DeliveryDto> deliveries;

}
