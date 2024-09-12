package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;


    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login (@RequestBody CustomerDto customerDto, HttpServletRequest request) throws Exception {
        try {
            Authentication authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerDto.getEmail(), customerDto.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authObject);

            // Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
