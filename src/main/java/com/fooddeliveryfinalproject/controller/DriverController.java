package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.service.DriverService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/drivers")
public class DriverController extends LoginImplController<DriverService, DriverDto> {

    public DriverController(AuthenticationManager authenticationManager,
                            JWTUtilService jwtUtilService,
                            UserService userService,
                            DriverService service) {
        super(authenticationManager, jwtUtilService, userService, service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<Iterable<DriverDto>> getAllCustomers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getAllDriver(PageRequest.of(page, size)).getContent(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getDriverById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("id") Long id, @RequestBody DriverDto driverDto) {
        service.updateDriver(id, driverDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") Long id) {
        service.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
