package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.model.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartConverter implements Converter<Cart, CartDto> {
    @Autowired
    CartItemConverter cartItemConverter;


    @Override
    public Cart convertToEntity(CartDto model, Cart entity) {
        entity.setCartId(model.getCartId());
        entity.setCount(model.getCount());
        return entity;
    }

    @Override
    public CartDto convertToModel(Cart entity, CartDto model) {
        model.setCartId(entity.getCartId());
        model.setCount(entity.getCount());
        if (entity.getItems() != null) {
            model.setCartItems(entity.getItems().stream()
                    .map(cartItem -> cartItemConverter.convertToModel(cartItem, new CartItemDto()))
                    .toList()
            );
        }
        return model;
    }
}
