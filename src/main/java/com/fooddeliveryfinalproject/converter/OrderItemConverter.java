package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.OrderItem;
import com.fooddeliveryfinalproject.model.OrderItemDto;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import com.fooddeliveryfinalproject.repository.OrderRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter implements Converter<OrderItem, OrderItemDto> {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Override
    public OrderItem convertToEntity(OrderItemDto model, OrderItem entity) {
        entity.setOrder(orderRepo.findById(model.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Not found order")));

        entity.setMenuItem(menuItemRepo.findById(model.getMenuItemId())
                .orElseThrow(() -> new EntityNotFoundException("Not found menu item")));

        entity.setQty(model.getQuantity());

        return entity;
    }

    @Override
    public OrderItemDto convertToModel(OrderItem entity, OrderItemDto model) {
        model.setOrderId(entity.getOrder().getOrderId());
        model.setMenuItemId(entity.getMenuItem().getMenuItemId());
        model.setQuantity(entity.getQty());
        return model;
    }
}
