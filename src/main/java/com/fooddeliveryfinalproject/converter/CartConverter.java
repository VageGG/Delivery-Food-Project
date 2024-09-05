package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartConverter implements Converter<Cart, CartDto> {

    private OrderConverter orderConverter;

    private final MenuItemConverter menuItemConverter;

    public CartConverter(MenuItemConverter menuItemConverter) {
        this.menuItemConverter = menuItemConverter;
    }

    @Autowired
    @Lazy
    public void setOrderConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Override
    public Cart convertToEntity(CartDto model, Cart entity) {
        entity.setCartId(model.getCartId());

        if (model.getOrderDto() != null) {
            entity.setOrder(orderConverter.convertToEntity(model.getOrderDto(), new Order()));
        }

        if (model.getItemsDto() != null) {
            List<MenuItem> menuItems = new ArrayList<>();
            for (MenuItemDto menuItemDto : model.getItemsDto()) {
                menuItems.add(menuItemConverter.convertToEntity(menuItemDto, new MenuItem()));
            }
            entity.setItems(menuItems);
        }

        entity.setCount(model.getCount());
        return entity;
    }

    @Override
    public CartDto convertToModel(Cart entity, CartDto model) {
        model.setCartId(entity.getCartId());

        if (entity.getOrder() != null) {
            model.setOrderDto(orderConverter.convertToModel(entity.getOrder(), new OrderDto()));
        }

        if (entity.getItems() != null) {
            List<MenuItemDto> menuItemsDto = new ArrayList<>();
            for (MenuItem menuItem : entity.getItems()) {
                menuItemsDto.add(menuItemConverter.convertToModel(menuItem, new MenuItemDto()));
            }
            model.setItemsDto(menuItemsDto);
        }

        model.setCount(entity.getCount());
        return model;
    }
}
