package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.DriverDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriverConverterTest {

    private DriverConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DriverConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        DriverDto dto = new DriverDto();
        dto.setId(1L);
        dto.setUsername("test");
        dto.setEmail("test@example.com");
        dto.setPassword("test-Password13");
        dto.setPhoneNumber("1234567890");
        dto.setRole(User.Role.DRIVER);

        Driver entity = new Driver();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertEquals("test", entity.getUsername());
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("test-Password13", entity.getPassword());
        assertEquals("1234567890", entity.getPhoneNumber());
        assertEquals(User.Role.DRIVER, entity.getRole());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Driver entity = new Driver();
        entity.setId(1L);
        entity.setUsername("test");
        entity.setEmail("test@example.com");
        entity.setPassword("test-Password13");
        entity.setPhoneNumber("1234567890");
        entity.setRole(User.Role.DRIVER);

        DriverDto dto = new DriverDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
        assertEquals("test", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("test-Password13", dto.getPassword());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals(User.Role.DRIVER, dto.getRole());
    }
}
