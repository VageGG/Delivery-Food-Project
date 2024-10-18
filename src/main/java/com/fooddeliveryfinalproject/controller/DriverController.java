package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/drivers")
@Validated
public class DriverController extends RegisterImplController<DriverService, DriverDto> {

    public DriverController(DriverService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<Iterable<DriverDto>> getAllDrivers(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") @Min(10) @Max(100) int size) {
        return new ResponseEntity<>(service.getAllDriver(PageRequest.of(page, size)).getContent(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable("id") @Min(1) Long id) {
        return new ResponseEntity<>(service.getDriverById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<HttpStatus> updateDriver(@PathVariable("id") @Min(1) Long id, @RequestBody @Valid DriverDto driverDto) {
        service.updateDriver(id, driverDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDriver(@PathVariable("id") @Min(1) Long id) {
        service.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve-driver/{driverId}")
    public ResponseEntity<?> approveDriver(@PathVariable("driverId") @Min(1) Long driverId) {
        service.approveDriver(driverId);
        return ResponseEntity.ok("Driver approved");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/rejected-driver/{driverId}")
    public ResponseEntity<?> rejectedDriver(@PathVariable("driverId") @Min(1) Long driverId) {
        service.rejectedDriver(driverId);
        return ResponseEntity.ok("Driver rejected");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending-drivers")
    public ResponseEntity<List<DriverDto>> getPendingDrivers() {
        return new ResponseEntity<>(service.getAllPendingDrivers(), HttpStatus.OK);
    }

    @Override
    protected User.Role getExpectedRole() {
        return User.Role.DRIVER;
    }
}
