package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.MenuConverter;
import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.repository.MenuRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuRepo repo;

    @Autowired
    private MenuConverter converter;

    @Transactional
    public MenuDto createMenu(MenuDto menuDto) {
        Menu menu = this.converter.convertToEntity(menuDto, new Menu());
        return this.converter.convertToModel(this.repo.save(menu), new MenuDto());
    }

    public MenuDto getMenuById(Long id) {
        return this.converter.convertToModel
                (
                        this.repo.findById(id).orElseThrow(() ->
                                new RuntimeException("menu not found")), new MenuDto()
                );
    }

    public List<MenuDto> getAllMenus() {
        return this.repo.findAll().stream()
                .map(menus -> this.converter.convertToModel(menus, new MenuDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public MenuDto updateMenu(MenuDto menuDto) {
        getMenuById(menuDto.getId());
        Menu menu = this.converter.convertToEntity(menuDto, new Menu());
        return this.converter.convertToModel(this.repo.save(menu), new MenuDto());
    }

    @Transactional
    public void deleteMenu(long id) {
        Menu menu = this.converter.convertToEntity(getMenuById(id), new Menu());
        this.repo.delete(menu);
    }
}
