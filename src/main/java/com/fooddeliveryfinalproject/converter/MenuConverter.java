package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.model.MenuDto;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter implements Converter<Menu, MenuDto>{
    @Override
    public Menu convertToEntity(MenuDto model, Menu entity) {
        entity.setMenuCategory(model.getMenuCategoryDto());
        entity.setRestaurantBranch(model.getRestaurantBranchDto());
        return entity;
    }

    @Override
    public MenuDto convertToModel(Menu entity, MenuDto model) {
        model.setMenuCategoryDto(entity.getMenuCategory());
        model.setRestaurantBranchDto(entity.getRestaurantBranch());
        return model;
    }
}
