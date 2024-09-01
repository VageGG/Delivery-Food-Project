package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.PaymentMethod;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private long paymentId;
    private Order order;
    private double totalAmount;
    private PaymentMethod paymentMethod;
}
