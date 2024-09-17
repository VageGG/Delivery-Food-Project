package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.UserDto;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import com.fooddeliveryfinalproject.service.ValidUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.NoSuchAlgorithmException;
@AllArgsConstructor
public abstract class LoginImplController<S extends ValidUser, T extends UserDto> {

    private AuthenticationManager authenticationManager;

    private JWTUtilService jwtUtilService;

    private UserService userService;

    public S service;
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody T dto) {
        try {
            service.addUser(dto);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody T dto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(dto.getEmail());
        final String jwt = jwtUtilService.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }
}
