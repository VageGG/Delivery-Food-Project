package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import org.springframework.stereotype.Component;


@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    @Override
    public Restaurant convertToEntity(RestaurantDto model, Restaurant entity) {
        entity.setRestId(model.getRestId());
        entity.setName(model.getName());
        return entity;
    }

    @Override
    public RestaurantDto convertToModel(Restaurant entity, RestaurantDto model) {
        model.setRestId(entity.getRestId());
        model.setName(entity.getName());
        return model;
    }

}
