package com.fooddeliveryfinalproject.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {

    private Long id;

    private String username;

    private String email;

    private String phoneNumber;

    private List<OrderDto> orders;

    private List<AddressDto> addresses;

    private List<PaymentMethodDto> paymentMethods;
}
