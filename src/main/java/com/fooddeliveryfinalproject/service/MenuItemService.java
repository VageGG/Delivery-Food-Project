package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {
    @Autowired
    private MenuItemRepo repo;
    @Autowired
    private MenuItemConverter converter;

    @Transactional
    public MenuItemDto createMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = this.converter.convertToEntity(menuItemDto, new MenuItem());
        return this.converter.convertToModel(this.repo.save(menuItem), new MenuItemDto());
    }

    public MenuItemDto getMenuItemById(Long id) {
        return this.converter.convertToModel
                (
                        this.repo.findById(id).orElseThrow(() ->
                                new RuntimeException("menu item not found")), new MenuItemDto()
                );
    }

    public List<MenuItemDto> getAllMenuItems() {
        return this.repo.findAll().stream()
                .map(menuItems -> this.converter.convertToModel(menuItems, new MenuItemDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuItemDto updateMenuItem(MenuItemDto menuItemDto) {
        getMenuItemById(menuItemDto.getMenuItemId());
        MenuItem menuItem = this.converter.convertToEntity(menuItemDto, new MenuItem());
        return this.converter.convertToModel(this.repo.save(menuItem), new MenuItemDto());
    }

    @Transactional
    public void deleteMenuItem(long id) {
        MenuItem menuItem = this.converter.convertToEntity(getMenuItemById(id), new MenuItem());
        this.repo.delete(menuItem);
    }
}