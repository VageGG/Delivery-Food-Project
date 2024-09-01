package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.OrderCart;
import com.fooddeliveryfinalproject.model.OrderCartDto;

public class OrderCartConverter implements Converter<OrderCart, OrderCartDto> {

    @Override
    public OrderCart convertToEntity(OrderCartDto orderCartDto, OrderCart orderCart ) {
        orderCart.setCartId(orderCartDto.getCartId());
        orderCart.setOrder(orderCartDto.getOrder());
        orderCart.setItems(orderCartDto.getItems());
        orderCart.setCount(orderCartDto.getCount());
        return orderCart;
    }

    @Override
    public OrderCartDto convertToModel(OrderCart orderCart, OrderCartDto orderCartDto) {
        orderCartDto.setCartId(orderCart.getCartId());
        orderCartDto.setOrder(orderCartDto.getOrder());
        orderCartDto.setItems(orderCartDto.getItems());
        orderCartDto.setCount(orderCart.getCount());
        return orderCartDto;
    }
}
