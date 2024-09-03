package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.OrderCart;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.model.OrderCartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class MenuItemConverter implements Converter<MenuItem, MenuItemDto> {

    private final MenuCategoryConverter menuCategoryConverter;

    private OrderCartConverter orderCartConverter;

    public MenuItemConverter(MenuCategoryConverter menuCategoryConverter) {
        this.menuCategoryConverter = menuCategoryConverter;
    }

    @Autowired
    @Lazy
    public void setOrderCartConverter(OrderCartConverter orderCartConverter) {
        this.orderCartConverter = orderCartConverter;
    }

    @Override
    public MenuItem convertToEntity(MenuItemDto model, MenuItem entity) {
        entity.setMenuItemId(model.getMenuItemId());
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());

        if (model.getMenuCategoryDto() != null) {
            entity.setMenuCategory(menuCategoryConverter.convertToEntity(model.getMenuCategoryDto(), new MenuCategory()));
        }

        if (model.getOrderCartDto() != null) {
            entity.setOrderCart(orderCartConverter.convertToEntity(model.getOrderCartDto(), new OrderCart()));
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

        if (entity.getOrderCart()!= null) {
            model.setOrderCartDto(orderCartConverter.convertToModel(entity.getOrderCart(), new OrderCartDto()));
        }

        model.setDescription(entity.getDescription());

        return model;
    }

}
