package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.model.MenuDto;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter implements Converter<Menu, MenuDto> {

    @Override
    public Menu convertToEntity(MenuDto model, Menu entity) {
        entity.setMenuId(model.getId());
        return entity;
    }

    @Override
    public MenuDto convertToModel(Menu entity, MenuDto model) {
        model.setId(entity.getMenuId());
        return model;
    }
}
