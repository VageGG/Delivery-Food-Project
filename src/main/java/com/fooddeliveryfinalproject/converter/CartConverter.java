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

    private final CartItemConverter cartItemConverter;

    @Autowired
    @Lazy
    private CustomerConverter customerConverter;

    public CartConverter(CartItemConverter cartItemConverter) {
        this.cartItemConverter = cartItemConverter;
    }

    @Override
    public Cart convertToEntity(CartDto model, Cart entity) {
        entity.setCartId(model.getCartId());

        if (model.getItemsDto() != null) {
            List<CartItem> cartItems = cartItemConverter.convertToEntityList(model.getItemsDto(), CartItem::new);
            entity.setItems(cartItems);
        }

        if (model.getCustomerDto() != null) {
            entity.setCustomer(customerConverter.convertToEntity(model.getCustomerDto(), new Customer()));
        }

        entity.setCount(model.getCount());
        return entity;
    }

    @Override
    public CartDto convertToModel(Cart entity, CartDto model) {
        model.setCartId(entity.getCartId());

        if (entity.getItems() != null) {
            List<CartItemDto> cartItemsDto = cartItemConverter.convertToModelList(entity.getItems(), CartItemDto::new);
            model.setItemsDto(cartItemsDto);
        }

        if (entity.getCustomer()!= null) {
            model.setCustomerDto(customerConverter.convertToModel(entity.getCustomer(), new CustomerDto()));
        }

        model.setCount(entity.getCount());
        return model;
    }
}
