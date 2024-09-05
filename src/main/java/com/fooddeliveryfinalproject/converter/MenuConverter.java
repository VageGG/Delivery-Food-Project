package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuConverter implements Converter<Menu, MenuDto> {

    @Autowired
    @Lazy
    private MenuCategoryConverter menuCategoryConverter;

    private final RestaurantBranchConverter restaurantBranchConverter;

    public MenuConverter(RestaurantBranchConverter restaurantBranchConverter) {
        this.restaurantBranchConverter = restaurantBranchConverter;
    }


    @Override
    public Menu convertToEntity(MenuDto model, Menu entity) {
        entity.setMenuId(model.getId());

        if (model.getRestaurantBranchDto() != null) {
            entity.setRestaurantBranch(restaurantBranchConverter.convertToEntity(model.getRestaurantBranchDto(),
                    new RestaurantBranch()));
        }

        if (model.getMenuCategoriesDto() != null) {
            List<MenuCategory> menuCategories = menuCategoryConverter.convertToEntityList(model.getMenuCategoriesDto(), MenuCategory::new);
            entity.setMenuCategories(menuCategories);
        }

        return entity;
    }

    @Override
    public MenuDto convertToModel(Menu entity, MenuDto model) {
        model.setId(entity.getMenuId());

        if (entity.getRestaurantBranch() != null) {
            model.setRestaurantBranchDto(restaurantBranchConverter.convertToModel(entity.getRestaurantBranch(),
                    new RestaurantBranchDto()));
        }

        if (entity.getMenuCategories() != null) {
            List<MenuCategoryDto> menuCategoryDtos = menuCategoryConverter.convertToModelList(entity.getMenuCategories(), MenuCategoryDto::new);
            model.setMenuCategoriesDto(menuCategoryDtos);
        }

        return model;
    }
}
