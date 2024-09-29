package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.UserDto;
import com.fooddeliveryfinalproject.service.ValidUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;
@AllArgsConstructor
public abstract class RegisterImplController<S extends ValidUser, T extends UserDto> {

    public S service;
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody T dto) {

        if (!dto.getRole().equals(getExpectedRole())) {
            throw new RuntimeException("Role mismatch. You are not allowed to register this role via this endpoint.");
        }

        try {
            service.addUser(dto);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected abstract User.Role getExpectedRole();
}
