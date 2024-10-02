package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemConverterTest {

    private MenuItemConverter converter;

    @BeforeEach
    void setUp() {
        converter = new MenuItemConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        MenuItemDto dto = new MenuItemDto();
        dto.setMenuItemId(1L);
        dto.setName("test");
        dto.setDescription("test description");

        MenuItem entity = new MenuItem();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getMenuItemId());
        assertEquals("test", entity.getName());
        assertEquals("test description", entity.getDescription());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        MenuItem entity = new MenuItem();
        entity.setMenuItemId(1L);
        entity.setName("test");
        entity.setDescription("test description");

        MenuItemDto dto = new MenuItemDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getMenuItemId());
        assertEquals("test", dto.getName());
        assertEquals("test description", dto.getDescription());
    }
}
