package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.service.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController extends RegisterImplController<CustomerService, CustomerDto> {

    public CustomerController(CustomerService service) {
        super(service);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size) {
        List<CustomerDto> customerDtos = service.getAllCustomer(PageRequest.of(page, size))
                .stream()
                .collect(Collectors.toList());
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getCustomer(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/address/{customerId}")
    public ResponseEntity<HttpStatus> addAddress(@PathVariable("customerId") Long customerId, @RequestBody AddressDto addressDto) {
        service.addAddressForCustomer(customerId, addressDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    protected User.Role getExpectedRole() {
        return User.Role.CUSTOMER;
    }
}
