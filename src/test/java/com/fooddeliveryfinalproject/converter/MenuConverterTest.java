package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.model.MenuDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuConverterTest {

    private MenuConverter converter;

    @BeforeEach
    void setUp() {
        converter = new MenuConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        MenuDto dto = new MenuDto();
        dto.setId(1L);

        Menu entity = new Menu();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getMenuId());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        Menu entity = new Menu();
        entity.setMenuId(1L);

        MenuDto dto = new MenuDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
    }
}
