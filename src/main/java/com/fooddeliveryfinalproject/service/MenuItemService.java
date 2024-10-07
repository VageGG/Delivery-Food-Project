package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class MenuItemService {

    private final MenuItemRepo repo;

    private final MenuItemConverter converter;

    @Autowired
    public MenuItemService(MenuItemRepo repo, MenuItemConverter converter) {
        this.repo = repo;
        this.converter = converter;
    }

    @Transactional
    public MenuItemDto createMenuItem(@Valid MenuItemDto menuItemDto) {
        MenuItem menuItem = this.converter.convertToEntity(menuItemDto, new MenuItem());
        return this.converter.convertToModel(this.repo.save(menuItem), new MenuItemDto());
    }

    @Transactional(readOnly = true)
    public MenuItemDto getMenuItemById(@Min(1) Long id) {
        return this.converter.convertToModel
                (
                        this.repo.findById(id).orElseThrow(() ->
                                new RuntimeException("menu item not found")), new MenuItemDto()
                );
    }

    @Transactional(readOnly = true)
    public List<MenuItemDto> getAllMenuItems() {
        return this.repo.findAll().stream()
                .map(menuItems -> this.converter.convertToModel(menuItems, new MenuItemDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuItemDto updateMenuItem(@Valid MenuItemDto menuItemDto) {
        getMenuItemById(menuItemDto.getMenuItemId());
        MenuItem menuItem = this.converter.convertToEntity(menuItemDto, new MenuItem());
        return this.converter.convertToModel(this.repo.save(menuItem), new MenuItemDto());
    }

    @Transactional
    public void deleteMenuItem(@Min(1) long id) {
        MenuItem menuItem = this.converter.convertToEntity(getMenuItemById(id), new MenuItem());
        this.repo.delete(menuItem);
    }
}