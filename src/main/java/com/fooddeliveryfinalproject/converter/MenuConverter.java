package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter implements Converter<Menu, MenuDto> {

    private final MenuCategoryConverter menuCategoryConverter;

    private final RestaurantBranchConverter restaurantBranchConverter;

    public MenuConverter(MenuCategoryConverter menuCategoryConverter,
                         RestaurantBranchConverter restaurantBranchConverter) {
        this.menuCategoryConverter = menuCategoryConverter;
        this.restaurantBranchConverter = restaurantBranchConverter;
    }

    @Override
    public Menu convertToEntity(MenuDto model, Menu entity) {
        entity.setMenuId(model.getId());

        if (model.getRestaurantBranchDto() != null) {
            entity.setRestaurantBranch(restaurantBranchConverter.convertToEntity(model.getRestaurantBranchDto(),
                    new RestaurantBranch()));
        }

        if (model.getMenuCategoryDto() != null) {
            entity.setMenuCategory(menuCategoryConverter.convertToEntity(model.getMenuCategoryDto(),
                    new MenuCategory()));
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

        if (entity.getMenuCategory() != null) {
            model.setMenuCategoryDto(menuCategoryConverter.convertToModel(entity.getMenuCategory(),
                    new MenuCategoryDto()));
        }

        return model;
    }
}
