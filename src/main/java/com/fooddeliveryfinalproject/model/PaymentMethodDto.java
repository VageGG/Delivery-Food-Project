package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto {

    private Long id;

    private Payment.PaymentMethodType paymentMethodType;

    private String details;
}
