package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import org.springframework.stereotype.Component;

@Component
public class MenuItemConverter implements Converter<MenuItem, MenuItemDto> {

    private final MenuConverter menuConverter;

    public MenuItemConverter(MenuConverter menuConverter) {
        this.menuConverter = menuConverter;
    }

    @Override
    public MenuItem convertToEntity(MenuItemDto model, MenuItem entity) {
        entity.setMenuItemId(model.getMenuItemId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());

        if (model.getMenuDto() != null) {
            entity.setMenu(menuConverter.convertToEntity(model.getMenuDto(), new Menu()));
        }

        entity.setDescription(model.getDescription());

        return entity;
    }

    @Override
    public MenuItemDto convertToModel(MenuItem entity, MenuItemDto model) {
        model.setMenuItemId(entity.getMenuItemId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());

        if (entity.getMenu() != null) {
            model.setMenuDto(menuConverter.convertToModel(entity.getMenu(), new MenuDto()));
        }

        model.setDescription(entity.getDescription());

        return model;
    }

}
