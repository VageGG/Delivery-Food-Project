package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long paymentId;

    private Double totalAmount;

    private Payment.PaymentMethodType paymentMethod;
}
