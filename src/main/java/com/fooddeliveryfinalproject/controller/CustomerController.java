package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.CustomerService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<Iterable<CustomerDto>> getAllCustomers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getAllCustomer(PageRequest.of(page, size)).getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getCustomer(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto customerDto) {
        service.updateCustomer(id, customerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") Long id) {
        service.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/address/{customerId}")
    public ResponseEntity<HttpStatus> addAddress(@PathVariable("customerId") Long customerId, @RequestBody AddressDto addressDto) {
        service.addAddressForCustomer(customerId, addressDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/payment-method/{customerId}")
    public ResponseEntity<HttpStatus> addPaymentMethod(@PathVariable("customerId") Long customerId, @RequestBody PaymentMethodDto paymentMethodDto) {
        service.addPaymentMethod(customerId, paymentMethodDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<Iterable<OrderDto>> getCustomerOrders(@PathVariable("id") Long customerId,
                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getCustomerOrders(customerId,
                PageRequest.of(page, size)).getContent(),
        HttpStatus.OK);
    }

    @GetMapping(value = "/cart/{id}")
    public ResponseEntity<CartDto> getCustomerCart(@PathVariable("id") Long customerId) {
        return new ResponseEntity<>(service.getCustomerCart(customerId), HttpStatus.OK);
    }
}
