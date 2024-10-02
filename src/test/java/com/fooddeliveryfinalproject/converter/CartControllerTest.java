package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.CartDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartControllerTest {

    private CartConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CartConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        CartDto dto = new CartDto();
        dto.setCartId(1L);
        dto.setCount(1);

        Cart entity = new Cart();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getCartId());
        assertEquals(1, entity.getCount());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Cart entity = new Cart();
        entity.setCartId(1L);
        entity.setCount(1);

        CartDto dto = new CartDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getCartId());
        assertEquals(1, dto.getCount());
    }
}
