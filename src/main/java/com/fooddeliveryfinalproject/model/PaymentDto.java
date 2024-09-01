package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Payment;
import lombok.*;

@Data
public class PaymentDto {

    private Long paymentId;

    private OrderDto orderDto;

    private Double totalAmount;

    private Payment.PaymentMethodType paymentMethod;
}
