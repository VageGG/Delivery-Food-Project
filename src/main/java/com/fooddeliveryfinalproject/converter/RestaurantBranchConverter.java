package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBranchConverter implements Converter<RestaurantBranch, RestaurantBranchDto>{
    @Override
    public RestaurantBranch convertToEntity(RestaurantBranchDto model, RestaurantBranch entity) {
        entity.setRestaurant(model.getRestaurantDto());
        entity.setMenu(model.getMenuDto());
        entity.setLocation(model.getLocation());
        entity.setPhoneNumber(model.getPhoneNumber());
        return entity;
    }
    @Override
    public RestaurantBranchDto convertToModel(RestaurantBranch entity, RestaurantBranchDto model) {
        model.setRestaurantDto(entity.getRestaurant());
        model.setMenuDto(entity.getMenu());
        model.setLocation(entity.getLocation());
        model.setPhoneNumber(entity.getPhoneNumber());
        return model;
    }
}
