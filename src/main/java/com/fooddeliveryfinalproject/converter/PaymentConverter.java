package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.Payment;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.model.PaymentDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter implements Converter<Payment, PaymentDto> {

    private final OrderConverter orderConverter;

    public PaymentConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Override
    public Payment convertToEntity(PaymentDto model, Payment entity) {
        entity.setPaymentId(entity.getPaymentId());

        if (model.getOrderDto() != null) {
            entity.setOrder(orderConverter.convertToEntity(model.getOrderDto(), new Order()));
        }

        entity.setTotalAmount(model.getTotalAmount());
        entity.setPaymentMethod(model.getPaymentMethod());
        return entity;
    }

    @Override
    public PaymentDto convertToModel(Payment entity, PaymentDto model) {
        model.setPaymentId(entity.getPaymentId());

        if (entity.getOrder()!= null) {
            model.setOrderDto(orderConverter.convertToModel(entity.getOrder(), new OrderDto()));
        }

        model.setTotalAmount(entity.getTotalAmount());
        model.setPaymentMethod(entity.getPaymentMethod());
        return model;
    }
}
