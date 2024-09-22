package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


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
