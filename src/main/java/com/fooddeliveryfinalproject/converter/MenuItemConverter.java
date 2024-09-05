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

        if (model.getMenuCategoryDto() != null) {
            entity.setMenuCategory(menuCategoryConverter.convertToEntity(model.getMenuCategoryDto(), new MenuCategory()));
        }

        if (model.getCartDtos() != null) {
            List<CartItem> cartItems = cartItemConverter.convertToEntityList(model.getCartDtos(), CartItem::new);
            entity.setCarts(cartItems);
        }

        if (model.getOrderDtos() != null) {
            List<OrderItem> orderItems = orderItemConverter.convertToEntityList(model.getOrderDtos(), OrderItem::new);
            entity.setOrders(orderItems);
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

        if (entity.getCarts() != null) {
            List<CartItemDto> cartDtos = cartItemConverter.convertToModelList(entity.getCarts(), CartItemDto::new);
            model.setCartDtos(cartDtos);
        }

        if (entity.getOrders() != null) {
            List<OrderItemDto> orderDtos = orderItemConverter.convertToModelList(entity.getOrders(), OrderItemDto::new);
            model.setOrderDtos(orderDtos);
        }

        model.setDescription(entity.getDescription());

        return model;
    }

}
