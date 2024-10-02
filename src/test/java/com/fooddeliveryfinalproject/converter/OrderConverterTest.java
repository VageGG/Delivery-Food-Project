package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderConverterTest {

    private OrderConverter converter;

    @BeforeEach
    void setUp() {
        converter = new OrderConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        OrderDto dto = new OrderDto();
        dto.setOrderId(1L);
        dto.setStatus(Order.OrderStatus.PENDING);

        Order entity = new Order();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getOrderId());
        assertEquals(Order.OrderStatus.PENDING, entity.getStatus());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Order entity = new Order();
        entity.setOrderId(1L);
        entity.setStatus(Order.OrderStatus.PENDING);

        OrderDto dto = new OrderDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getOrderId());
        assertEquals(Order.OrderStatus.PENDING, dto.getStatus());
    }
}
