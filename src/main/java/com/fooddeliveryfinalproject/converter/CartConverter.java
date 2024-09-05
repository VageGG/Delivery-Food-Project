package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.CartItemDto;
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

    private final CartItemConverter cartItemConverter;

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

        model.setCount(entity.getCount());
        return model;
    }
}
