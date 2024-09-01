package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodConverter implements Converter<PaymentMethod, PaymentMethodDto> {

    private CustomerConverter customerConverter;

    public PaymentMethodConverter(CustomerConverter customerConverter) {
        this.customerConverter = customerConverter;
    }
    @Override
    public PaymentMethod convertToEntity(PaymentMethodDto model, PaymentMethod entity) {
        entity.setId(model.getId());
        entity.setPaymentMethodType(entity.getPaymentMethodType());
        entity.setDetails(model.getDetails());

        if(model.getCustomerDto() != null) {
            entity.setCustomer(customerConverter.convertToEntity(model.getCustomerDto(), new Customer()));
        }

        return entity;
    }

    @Override
    public PaymentMethodDto convertToModel(PaymentMethod entity, PaymentMethodDto model) {
        model.setId(entity.getId());
        model.setPaymentMethodType(entity.getPaymentMethodType());
        model.setDetails(entity.getDetails());

        if(entity.getCustomer()!= null) {
            model.setCustomerDto(customerConverter.convertToModel(entity.getCustomer(), new CustomerDto()));
        }

        return model;
    }
}
