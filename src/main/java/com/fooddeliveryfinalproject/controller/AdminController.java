package com.fooddeliveryfinalproject.controller;


import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.service.AdminService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admins")
public class AdminController extends LoginImplController<AdminService, AdminDto>{
    public AdminController(AuthenticationManager authenticationManager,
                           JWTUtilService jwtUtilService,
                           UserService userService,
                           AdminService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }

}
