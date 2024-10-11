package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Converter<Customer, CustomerDto> {

    @Override
    public Customer convertToEntity(CustomerDto model, Customer entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setRole(model.getRole());

        return entity;
    }

    @Override
    public CustomerDto convertToModel(Customer entity, CustomerDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setRole(entity.getRole());

        return model;
    }
}
