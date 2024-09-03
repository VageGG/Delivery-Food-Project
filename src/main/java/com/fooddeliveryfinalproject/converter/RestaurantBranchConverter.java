package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBranchConverter implements Converter<RestaurantBranch, RestaurantBranchDto> {

    @Autowired
    private RestaurantConverter restaurantConverter;

    @Autowired
    @Lazy
    private MenuConverter menuConverter;


    @Override
    public RestaurantBranch convertToEntity(RestaurantBranchDto model, RestaurantBranch entity) {
        entity.setRestBranchId(model.getBranchId());

        if (model.getRestaurantDto() != null) {
            entity.setRestaurant(restaurantConverter.convertToEntity(model.getRestaurantDto(), new Restaurant()));
        }

        if (model.getMenuDto() != null) {
            entity.setMenu(menuConverter.convertToEntity(model.getMenuDto(), new Menu()));
        }

        entity.setLocation(model.getLocation());
        entity.setPhoneNumber(model.getPhoneNumber());
        return entity;
    }

    @Override
    public RestaurantBranchDto convertToModel(RestaurantBranch entity, RestaurantBranchDto model) {
        model.setBranchId(entity.getRestBranchId());

        if (entity.getRestaurant() != null) {
            model.setRestaurantDto(restaurantConverter.convertToModel(entity.getRestaurant(), new RestaurantDto()));
        }

        if (entity.getMenu() != null) {
            model.setMenuDto(menuConverter.convertToModel(entity.getMenu(), new MenuDto()));
        }

        model.setLocation(entity.getLocation());
        model.setPhoneNumber(entity.getPhoneNumber());
        return model;
    }
}
