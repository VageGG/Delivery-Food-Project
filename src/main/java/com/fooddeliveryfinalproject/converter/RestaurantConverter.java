package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    private final RestaurantBranchConverter restaurantBranchConverter;

    public RestaurantConverter(RestaurantBranchConverter restaurantBranchConverter) {
        this.restaurantBranchConverter = restaurantBranchConverter;

    }
    @Override
    public Restaurant convertToEntity(RestaurantDto model, Restaurant entity) {

        entity.setRestName(model.getRestName());
        entity.setRestaurantManager(model.getRestaurantManagerDto());

        if (model.getBranchesDto() != null) {
            List<RestaurantBranch> restaurantBranches = new ArrayList<>();
            for (RestaurantBranchDto restaurantBranchDto : model.getBranchesDto()) {
                restaurantBranches.add(restaurantBranchConverter.convertToEntity(restaurantBranchDto,new RestaurantBranch()));

            }
            entity.setBranches(restaurantBranches);
        }

        entity.setRating(model.getRating());
        return entity;
    }

    @Override
    public RestaurantDto convertToModel(Restaurant entity, RestaurantDto model) {
        model.setRestName(entity.getRestName());
        model.setRestaurantManagerDto(entity.getRestaurantManager());

        if (entity.getBranches() != null) {
            List<RestaurantBranchDto> restaurantBranchDtos = new ArrayList<>();
            for (RestaurantBranch restaurantBranch : entity.getBranches()) {
                restaurantBranchDtos.add(restaurantBranchConverter.convertToModel(restaurantBranch, new RestaurantBranchDto()));
            }
            model.setBranchesDto(restaurantBranchDtos);
        }

        model.setRating(entity.getRating());
        return model;
    }

}
