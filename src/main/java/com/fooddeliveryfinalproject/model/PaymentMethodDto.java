package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Payment;
import lombok.Data;

@Data
public class PaymentMethodDto {

    private Long id;

    private Payment.PaymentMethodType paymentMethodType;

    private String details;

    private CustomerDto customerDto;
}
