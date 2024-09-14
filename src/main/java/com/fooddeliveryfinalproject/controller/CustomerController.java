package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.service.CustomerService;
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
@RequestMapping(value = "/customers")
public class CustomerController extends LoginImplController<CustomerService, CustomerDto>{
    public CustomerController(AuthenticationManager authenticationManager,
                              JWTUtilService jwtUtilService,
                              UserService userService,
                              CustomerService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }

}
