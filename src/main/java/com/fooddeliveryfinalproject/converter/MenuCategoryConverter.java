package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuCategoryConverter implements Converter<MenuCategory, MenuCategoryDto> {

    private final MenuItemConverter menuItemConverter;

    private final MenuConverter menuConverter;

    public MenuCategoryConverter(MenuItemConverter menuItemConverter, MenuConverter menuConverter) {
        this.menuConverter = menuConverter;
        this.menuItemConverter = menuItemConverter;
    }

    @Override
    public MenuCategory convertToEntity(MenuCategoryDto model, MenuCategory entity) {
        entity.setCategoryId(model.getCategoryId());

        if (model.getItemsDto() != null) {
            List<MenuItem> menuItems = new ArrayList<>();
            for (MenuItemDto menuItemDto : model.getItemsDto()) {
                menuItems.add(menuItemConverter.convertToEntity(menuItemDto, new MenuItem()));
            }
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
            List<MenuItemDto> menuItemDtos = new ArrayList<>();
            for (MenuItem menuItem : entity.getItems()) {
                menuItemDtos.add(menuItemConverter.convertToModel(menuItem, new MenuItemDto()));
            }
            model.setItemsDto(menuItemDtos);
        }

        model.setName(entity.getName());

        if (entity.getMenu() != null) {
            model.setMenuDto(menuConverter.convertToModel(entity.getMenu(), new MenuDto()));
        }

        return model;
    }
}
