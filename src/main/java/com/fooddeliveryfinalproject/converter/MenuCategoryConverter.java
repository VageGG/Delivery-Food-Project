package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import org.springframework.stereotype.Component;

@Component
public class MenuCategoryConverter implements Converter<MenuCategory, MenuCategoryDto> {

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
