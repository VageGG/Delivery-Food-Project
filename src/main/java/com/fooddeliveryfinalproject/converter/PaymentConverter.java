package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Payment;
import com.fooddeliveryfinalproject.model.PaymentDto;

public class PaymentConverter implements Converter<Payment, PaymentDto> {
    @Override
    public Payment convertToEntity(PaymentDto paymentDto, Payment payment) {
        payment.setPaymentId(payment.getPaymentId());
        payment.setOrder(paymentDto.getOrder());
        payment.setTotalAmount(paymentDto.getTotalAmount());
        payment.setPaymentMethod(paymentDto.getPaymentMethod());
        return payment;
    }

    @Override
    public PaymentDto convertToModel(Payment payment, PaymentDto paymentDto) {
        paymentDto.setPaymentId(payment.getPaymentId());
        paymentDto.setOrder(payment.getOrder());
        paymentDto.setTotalAmount(payment.getTotalAmount());
        paymentDto.setPaymentMethod(payment.getPaymentMethod());
        return paymentDto;
    }
}
