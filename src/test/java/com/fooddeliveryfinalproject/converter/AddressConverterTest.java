package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.model.AddressDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressConverterTest {

    private AddressConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AddressConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        AddressDto dto = new AddressDto();
        dto.setId(1L);
        dto.setCountry("US");
        dto.setCity("NY");
        dto.setState("NY");
        dto.setStreet("123 Main St");
        dto.setHouseNumber("40");
        dto.setApartmentNumber("A");

        Address entity = new Address();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertEquals("US", entity.getCountry());
        assertEquals("NY", entity.getCity());
        assertEquals("NY", entity.getState());
        assertEquals("123 Main St", entity.getStreet());
        assertEquals("40", entity.getHouseNumber());
        assertEquals("A", entity.getApartmentNumber());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Address entity = new Address();
        entity.setId(1L);
        entity.setCountry("US");
        entity.setCity("NY");
        entity.setState("NY");
        entity.setStreet("123 Main St");
        entity.setHouseNumber("40");
        entity.setApartmentNumber("A");

        AddressDto dto = new AddressDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
        assertEquals("US", dto.getCountry());
        assertEquals("NY", dto.getCity());
        assertEquals("NY", dto.getState());
        assertEquals("123 Main St", dto.getStreet());
        assertEquals("40", dto.getHouseNumber());
        assertEquals("A", dto.getApartmentNumber());
    }
}
