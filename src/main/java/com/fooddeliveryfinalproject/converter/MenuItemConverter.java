package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import org.springframework.stereotype.Component;

@Component
public class MenuItemConverter implements Converter<MenuItem, MenuItemDto> {

    private final MenuCategoryConverter menuCategoryConverter;

    public MenuItemConverter(MenuCategoryConverter menuCategoryConverter) {
        this.menuCategoryConverter = menuCategoryConverter;
    }

    @Override
    public MenuItem convertToEntity(MenuItemDto model, MenuItem entity) {
        entity.setMenuItemId(model.getMenuItemId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());

        if (model.getMenuCategoryDto() != null) {
            entity.setMenuCategory(menuCategoryConverter.convertToEntity(model.getMenuCategoryDto(), new MenuCategory()));
        }

        entity.setDescription(model.getDescription());

        return entity;
    }

    @Override
    public MenuItemDto convertToModel(MenuItem entity, MenuItemDto model) {
        model.setMenuItemId(entity.getMenuItemId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());

        if (entity.getMenuCategory() != null) {
            model.setMenuCategoryDto(menuCategoryConverter.convertToModel(entity.getMenuCategory(), new MenuCategoryDto()));
        }

        model.setDescription(entity.getDescription());

        return model;
    }

}
