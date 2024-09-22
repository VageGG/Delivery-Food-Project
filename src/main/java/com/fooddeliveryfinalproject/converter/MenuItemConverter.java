package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuItemConverter implements Converter<MenuItem, MenuItemDto> {

    private final MenuCategoryConverter menuCategoryConverter;

    @Autowired
    @Lazy
    private CartItemConverter cartItemConverter;

    @Autowired
    @Lazy
    private OrderItemConverter orderItemConverter;

    public MenuItemConverter(MenuCategoryConverter menuCategoryConverter) {
        this.menuCategoryConverter = menuCategoryConverter;
    }

    @Autowired
    @Lazy
    public void setOrderCartConverter(CartConverter orderCartConverter) {
    }

    @Override
    public MenuItem convertToEntity(MenuItemDto model, MenuItem entity) {
        entity.setMenuItemId(model.getMenuItemId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setDescription(model.getDescription());

        return entity;
    }

    @Override
    public MenuItemDto convertToModel(MenuItem entity, MenuItemDto model) {
        model.setMenuItemId(entity.getMenuItemId());
        model.setName(entity.getName());
        model.setPrice(entity.getPrice());
        model.setDescription(entity.getDescription());

        return model;
    }

}
