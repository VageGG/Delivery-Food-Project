package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.RestaurantManagerService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
