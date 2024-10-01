package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerConverterTest {

    private CustomerConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CustomerConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        CustomerDto dto = new CustomerDto();
        dto.setId(1L);
        dto.setUsername("test");
        dto.setEmail("test@example.com");
        dto.setPassword("test-Password13");
        dto.setPhoneNumber("1234567890");
        dto.setRole(User.Role.CUSTOMER);

        Customer entity = new Customer();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertEquals("test", entity.getUsername());
        assertEquals("test@example.com", entity.getEmail());
        assertEquals("test-Password13", entity.getPassword());
        assertEquals("1234567890", entity.getPhoneNumber());
        assertEquals(User.Role.CUSTOMER, entity.getRole());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Customer entity = new Customer();
        entity.setId(1L);
        entity.setUsername("test");
        entity.setEmail("test@example.com");
        entity.setPassword("test-Password13");
        entity.setPhoneNumber("1234567890");
        entity.setRole(User.Role.CUSTOMER);

        CustomerDto dto = new CustomerDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
        assertEquals("test", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("test-Password13", dto.getPassword());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals(User.Role.CUSTOMER, dto.getRole());

    }
}
