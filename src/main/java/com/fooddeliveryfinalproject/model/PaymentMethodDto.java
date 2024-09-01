package com.fooddeliveryfinalproject.model;

import lombok.Data;

@Data
public class PaymentMethodDto {

    private Long id;

    private PaymentMethodType paymentMethodType;

    private String details;

    private CustomerDto customerDto;
}
