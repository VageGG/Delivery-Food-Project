package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import org.springframework.stereotype.Component;

@Component
public class RestManagerConverter implements Converter<RestaurantManager, RestaurantManagerDto> {

    private final RestaurantConverter restaurantConverter;

    public RestManagerConverter(RestaurantConverter restaurantConverter) {
        this.restaurantConverter = restaurantConverter;
    }

    @Override
    public RestaurantManager convertToEntity(RestaurantManagerDto model, RestaurantManager entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());

        if (model.getRestaurantDto() != null) {
            entity.setRestaurant(restaurantConverter.convertToEntity(model.getRestaurantDto(), new Restaurant()));
        }

        return entity;
    }

    @Override
    public RestaurantManagerDto convertToModel(RestaurantManager entity, RestaurantManagerDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());

        if (entity.getRestaurant() != null) {
            model.setRestaurantDto(restaurantConverter.convertToModel(entity.getRestaurant(), new RestaurantDto()));
        }

        return model;
    }
}
