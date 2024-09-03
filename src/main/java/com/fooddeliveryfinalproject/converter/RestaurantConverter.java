package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    private RestaurantBranchConverter restaurantBranchConverter;

    private RestManagerConverter restManagerConverter;

    @Autowired
    public void setRestManagerConverter(RestManagerConverter restManagerConverter) {
        this.restManagerConverter = restManagerConverter;
    }

    @Autowired
    @Lazy
    public void setRestaurantBranchConverter(RestaurantBranchConverter restaurantBranchConverter) {
        this.restaurantBranchConverter = restaurantBranchConverter;
    }
    @Override
    public Restaurant convertToEntity(RestaurantDto model, Restaurant entity) {
        entity.setRestId(model.getRestId());
        entity.setRestName(model.getRestName());

        if (model.getRestaurantManagerDto() != null) {
            entity.setRestaurantManager(restManagerConverter.convertToEntity(model.getRestaurantManagerDto(),
                    new RestaurantManager()));
        }

        if (model.getBranchesDto() != null) {
            List<RestaurantBranch> restaurantBranches = new ArrayList<>();
            for (RestaurantBranchDto restaurantBranchDto : model.getBranchesDto()) {
                restaurantBranches.add(restaurantBranchConverter.convertToEntity(restaurantBranchDto, new RestaurantBranch()));

            }
            entity.setBranches(restaurantBranches);
        }

        entity.setRating(model.getRating());
        return entity;
    }

    @Override
    public RestaurantDto convertToModel(Restaurant entity, RestaurantDto model) {
        model.setRestId(entity.getRestId());
        model.setRestName(entity.getRestName());

        if (entity.getRestaurantManager() != null) {
            model.setRestaurantManagerDto(restManagerConverter.convertToModel(entity.getRestaurantManager(),
                    new RestaurantManagerDto()));
        }

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
