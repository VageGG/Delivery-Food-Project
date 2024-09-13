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
public class CustomerController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JWTUtilService jwtUtil;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody CustomerDto customerDto) {
        try {
            customerService.addCustomer(customerDto);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerDto customerDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(customerDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}
