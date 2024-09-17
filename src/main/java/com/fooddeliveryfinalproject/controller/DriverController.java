package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.service.DriverService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/drivers")
public class DriverController extends LoginImplController<DriverService, DriverDto> {

    public DriverController(AuthenticationManager authenticationManager,
                            JWTUtilService jwtUtilService,
                            UserService userService,
                            DriverService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }

}
