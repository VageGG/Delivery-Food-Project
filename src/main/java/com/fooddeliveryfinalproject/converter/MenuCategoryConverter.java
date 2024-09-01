package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;

public class MenuCategoryConverter implements Converter<MenuCategory, MenuCategoryDto> {

    @Override
    public MenuCategory convertToEntity(MenuCategoryDto menuCategoryDto, MenuCategory menuCategory) {
        menuCategory.setCategoryId(menuCategoryDto.getCategoryId());
        menuCategory.setItems(menuCategoryDto.getItems());
        menuCategory.setName(menuCategoryDto.getName());
        menuCategory.setMenu(menuCategoryDto.getMenu());
        return menuCategory;
    }

    @Override
    public MenuCategoryDto convertToModel(MenuCategory menuCategory, MenuCategoryDto menuCategoryDto) {
        menuCategoryDto.setCategoryId(menuCategory.getCategoryId());
        menuCategoryDto.setItems(menuCategory.getItems());
        menuCategoryDto.setName(menuCategory.getName());
        menuCategoryDto.setMenu(menuCategory.getMenu());
        return menuCategoryDto;
    }
}
