package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.model.MenuDto;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter implements Converter<Menu, MenuDto>{
    @Override
    public Menu convertToEntity(MenuDto model, Menu entity) {
        entity.setMenuCategory(model.getMenuCategory());
        entity.setRestaurantBranch(model.getRestaurantBranch());
        return entity;
    }

    @Override
    public MenuDto convertToModel(Menu entity, MenuDto model) {
        model.setMenuCategory(entity.getMenuCategory());
        model.setRestaurantBranch(entity.getRestaurantBranch());
        return model;
    }
}
