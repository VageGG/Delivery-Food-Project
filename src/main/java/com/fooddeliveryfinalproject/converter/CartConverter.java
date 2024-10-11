package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.CartDto;
import org.springframework.stereotype.Component;

@Component
public class CartConverter implements Converter<Cart, CartDto> {

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
        return model;
    }
}
