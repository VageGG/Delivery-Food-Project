package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.service.CustomerService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController extends LoginImplController<CustomerService, CustomerDto>{
    public CustomerController(AuthenticationManager authenticationManager,
                              JWTUtilService jwtUtilService,
                              UserService userService,
                              CustomerService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }

    @PutMapping(value = "/address/{customerId}")
    public ResponseEntity<HttpStatus> addAddress(@PathVariable("customerId") Long customerId, @RequestBody AddressDto addressDto) {
        service.addAddress(customerId, addressDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
