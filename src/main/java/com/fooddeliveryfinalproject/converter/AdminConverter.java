package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.model.AdminDto;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter implements Converter<Admin, AdminDto> {

    @Override
    public Admin convertToEntity(AdminDto model, Admin entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setPhoneNumber(model.getPhoneNumber());
        entity.setRole(model.getRole());
        return entity;
    }

    @Override
    public AdminDto convertToModel(Admin entity, AdminDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setRole(entity.getRole());
        return model;
    }
}
