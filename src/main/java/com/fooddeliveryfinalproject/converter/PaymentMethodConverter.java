package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodConverter implements Converter<PaymentMethod, PaymentMethodDto> {


    @Override
    public PaymentMethod convertToEntity(PaymentMethodDto model, PaymentMethod entity) {
        entity.setId(model.getId());
        entity.setPaymentMethodType(model.getPaymentMethodType());
        entity.setDetails(model.getDetails());

        return entity;
    }

    @Override
    public PaymentMethodDto convertToModel(PaymentMethod entity, PaymentMethodDto model) {
        model.setId(entity.getId());
        model.setPaymentMethodType(entity.getPaymentMethodType());
        model.setDetails(entity.getDetails());

        return model;
    }
}
