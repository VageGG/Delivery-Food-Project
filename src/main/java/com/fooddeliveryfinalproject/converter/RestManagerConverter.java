package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RestManagerConverter implements Converter<RestaurantManager, RestaurantManagerDto> {

    @Override
    public RestaurantManager convertToEntity(RestaurantManagerDto model, RestaurantManager entity) {
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
    public RestaurantManagerDto convertToModel(RestaurantManager entity, RestaurantManagerDto model) {
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
