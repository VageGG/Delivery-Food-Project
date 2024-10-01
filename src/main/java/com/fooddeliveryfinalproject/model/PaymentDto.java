package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Payment;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long paymentId;

    private Double totalAmount;

    private Payment.PaymentMethodType paymentMethod;
}
