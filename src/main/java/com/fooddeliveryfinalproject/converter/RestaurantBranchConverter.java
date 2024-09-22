package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBranchConverter implements Converter<RestaurantBranch, RestaurantBranchDto> {

    @Override
    public RestaurantBranch convertToEntity(RestaurantBranchDto model, RestaurantBranch entity) {
        entity.setRestBranchId(model.getBranchId());
        entity.setPhoneNumber(model.getPhoneNumber());
        return entity;
    }

    @Override
    public RestaurantBranchDto convertToModel(RestaurantBranch entity, RestaurantBranchDto model) {
        model.setBranchId(entity.getRestBranchId());
        model.setPhoneNumber(entity.getPhoneNumber());
        return model;
    }
}
