package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.RestaurantManagerService;
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
@RequestMapping(value = "/managers")
public class RestaurantManagerController extends LoginImplController<RestaurantManagerService, RestaurantManagerDto> {

    public RestaurantManagerController(AuthenticationManager authenticationManager,
                                       JWTUtilService jwtUtilService,
                                       UserService userService,
                                       RestaurantManagerService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }
}
