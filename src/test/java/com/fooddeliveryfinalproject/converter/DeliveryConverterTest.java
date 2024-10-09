package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryConverterTest {

    private DeliveryConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DeliveryConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        DeliveryDto dto = new DeliveryDto();
        dto.setId(1L);
        dto.setTrackingNumber("1234");

        Delivery entity = new Delivery();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getDeliveryId());
        assertEquals("1234", entity.getTrackingNumber());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Delivery entity = new Delivery();
        entity.setDeliveryId(1L);
        entity.setTrackingNumber("1234");

        DeliveryDto dto = new DeliveryDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
        assertEquals("1234", dto.getTrackingNumber());
    }
}
