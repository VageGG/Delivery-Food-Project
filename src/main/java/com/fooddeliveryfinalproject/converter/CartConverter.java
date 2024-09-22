package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
