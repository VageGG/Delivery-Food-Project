package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.model.CartItemDto;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartItemConverter implements Converter<CartItem, CartItemDto> {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;
    @Override
    public CartItem convertToEntity(CartItemDto model, CartItem entity) {

        entity.setCart(cartRepo.findById(model.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Not found Cart")));

        entity.setMenuItem(menuItemRepo.findById(model.getMenuItemId())
                .orElseThrow(() -> new EntityNotFoundException("Not found MenuItem")));

        entity.setQty(model.getQty());

        return entity;
    }

    @Override
    public CartItemDto convertToModel(CartItem entity, CartItemDto model) {
        model.setCartId(entity.getCart().getCartId());
        model.setMenuItemId(entity.getMenuItem().getMenuItemId());
        model.setQty(entity.getQty());
        return model;
    }
}
