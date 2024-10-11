package com.fooddeliveryfinalproject.converter;


import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.model.DriverDto;
import org.springframework.stereotype.Component;

@Component
public class DriverConverter implements Converter<Driver, DriverDto> {

    @Override
    public Driver convertToEntity(DriverDto model, Driver entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setRole(model.getRole());
        entity.setStatus(model.getStatus());
        return entity;
    }

    @Override
    public DriverDto convertToModel(Driver entity, DriverDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setRole(entity.getRole());
        model.setStatus(entity.getStatus());
        return model;
    }

}
