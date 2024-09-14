package com.fooddeliveryfinalproject.controller;


import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.service.AdminService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

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
