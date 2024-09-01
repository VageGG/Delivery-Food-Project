package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import org.springframework.stereotype.Component;

@Component
public class MenuItemConverter implements Converter<MenuItem, MenuItemDto> {
    @Override
    public MenuItem convertToEntity(MenuItemDto model, MenuItem entity) {

        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setMenu(model.getMenu());
        entity.setDescription(model.getDescription());

        return entity;
    }
    @Override
    public MenuItemDto convertToModel(MenuItem entity, MenuItemDto model) {

        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setMenu(entity.getMenu());
        model.setDescription(entity.getDescription());

        return model;
    }

}
