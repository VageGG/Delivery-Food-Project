package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuCategoryConverter implements Converter<MenuCategory, MenuCategoryDto> {

    private MenuItemConverter menuItemConverter;

    private final MenuConverter menuConverter;


    public MenuCategoryConverter(MenuConverter menuConverter) {
        this.menuConverter = menuConverter;
    }

    @Autowired
    @Lazy
    public void setMenuItemConverter(MenuItemConverter menuItemConverter) {
        this.menuItemConverter = menuItemConverter;
    }

    @Override
    public MenuCategory convertToEntity(MenuCategoryDto model, MenuCategory entity) {
        entity.setCategoryId(model.getCategoryId());
        entity.setName(model.getName());
        return entity;
    }

    @Override
    public MenuCategoryDto convertToModel(MenuCategory entity, MenuCategoryDto model) {
        model.setCategoryId(entity.getCategoryId());
        model.setName(entity.getName());
        return model;
    }
}
