package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;

public class MenuCategoryConverter implements Converter<MenuCategory, MenuCategoryDto> {

    @Override
    public MenuCategory convertToEntity(MenuCategoryDto menuCategoryDto, MenuCategory menuCategory) {
        menuCategory.setCategoryId(menuCategoryDto.getCategoryId());
        menuCategory.setItems(menuCategoryDto.getItemsDto());
        menuCategory.setName(menuCategoryDto.getName());
        menuCategory.setMenu(menuCategoryDto.getMenuDto());
        return menuCategory;
    }

    @Override
    public MenuCategoryDto convertToModel(MenuCategory menuCategory, MenuCategoryDto menuCategoryDto) {
        menuCategoryDto.setCategoryId(menuCategory.getCategoryId());
        menuCategoryDto.setItemsDto(menuCategory.getItems());
        menuCategoryDto.setName(menuCategory.getName());
        menuCategoryDto.setMenuDto(menuCategory.getMenu());
        return menuCategoryDto;
    }
}
