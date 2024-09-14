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
public class RestaurantManagerController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RestaurantManagerService restaurantManagerService;

    @Autowired
    private JWTUtilService jwtUtil;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody RestaurantManagerDto restaurantManagerDto) {
        try {
            restaurantManagerService.addRestManager(restaurantManagerDto);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RestaurantManagerDto restaurantManagerDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(restaurantManagerDto.getEmail(), restaurantManagerDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(restaurantManagerDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}
