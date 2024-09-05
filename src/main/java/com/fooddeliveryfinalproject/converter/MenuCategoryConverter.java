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

        if (model.getItemsDto() != null) {
            List<MenuItem> menuItems = menuItemConverter.convertToEntityList(model.getItemsDto(), MenuItem::new);
            entity.setItems(menuItems);
        }

        entity.setName(model.getName());

        if (model.getMenuDto() != null) {
            entity.setMenu(menuConverter.convertToEntity(model.getMenuDto(), new Menu()));
        }

        return entity;
    }

    @Override
    public MenuCategoryDto convertToModel(MenuCategory entity, MenuCategoryDto model) {
        model.setCategoryId(entity.getCategoryId());

        if (entity.getItems() != null) {
            List<MenuItemDto> menuItemDtos = menuItemConverter.convertToModelList(entity.getItems(), MenuItemDto::new);
            model.setItemsDto(menuItemDtos);
        }

        model.setName(entity.getName());

        if (entity.getMenu() != null) {
            model.setMenuDto(menuConverter.convertToModel(entity.getMenu(), new MenuDto()));
        }

        return model;
    }
}
