package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AdminDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminConverterTest {
    private AdminConverter converter;

    @BeforeEach
    void setUp() {
        converter = new AdminConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        AdminDto dto = new AdminDto();
        dto.setId(1L);
        dto.setUsername("test");
        dto.setEmail("test@example.com");
        dto.setPassword("test-Password13");
        dto.setPhoneNumber("1234567890");
        dto.setRole(User.Role.ADMIN);

        Admin entity = new Admin();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertEquals("test", entity.getUsername());
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("test-Password13", entity.getPassword());
        assertEquals("1234567890", entity.getPhoneNumber());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Admin entity = new Admin();
        entity.setId(1L);
        entity.setUsername("test");
        entity.setEmail("test@example.com");
        entity.setPassword("test-Password13");
        entity.setPhoneNumber("1234567890");
        entity.setRole(User.Role.ADMIN);

        AdminDto dto = new AdminDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
        assertEquals("test", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("test-Password13", dto.getPassword());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals(User.Role.ADMIN, dto.getRole());

    }
}
