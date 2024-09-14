package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.User;
import lombok.Data;

@Data
public abstract class UserDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private User.Role role;
}
