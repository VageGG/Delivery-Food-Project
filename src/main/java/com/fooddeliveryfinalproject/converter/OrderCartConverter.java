package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.OrderCart;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.model.OrderCartDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderCartConverter implements Converter<OrderCart, OrderCartDto> {

    private final OrderConverter orderConverter;

    private final MenuItemConverter menuItemConverter;

    public OrderCartConverter(OrderConverter orderConverter, MenuItemConverter menuItemConverter) {
        this.orderConverter = orderConverter;
        this.menuItemConverter = menuItemConverter;
    }

    @Override
    public OrderCart convertToEntity(OrderCartDto model, OrderCart entity) {
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
    public OrderCartDto convertToModel(OrderCart entity, OrderCartDto model) {
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
