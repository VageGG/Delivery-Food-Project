package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuCategoryConverterTest {

    private MenuCategoryConverter converter;

    @BeforeEach
    void setUp() {
        converter = new MenuCategoryConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        MenuCategoryDto dto = new MenuCategoryDto();
        dto.setCategoryId(1L);
        dto.setName("test");

        MenuCategory entity = new MenuCategory();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getCategoryId());
        assertEquals("test", entity.getName());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        MenuCategory entity = new MenuCategory();
        entity.setCategoryId(1L);
        entity.setName("test");

        MenuCategoryDto dto = new MenuCategoryDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getCategoryId());
        assertEquals("test", dto.getName());
    }
}
