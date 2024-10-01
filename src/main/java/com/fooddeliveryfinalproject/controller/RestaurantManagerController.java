package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.service.RestaurantManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/managers")
public class RestaurantManagerController extends RegisterImplController<RestaurantManagerService, RestaurantManagerDto> {

    @Autowired
    public RestaurantManagerController(RestaurantManagerService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/list")
    public ResponseEntity<Iterable<RestaurantManagerDto>> getAllManagers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getAllManagers(PageRequest.of(page, size)).getContent(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantManagerDto> getManager(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getRestaurantManager(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<HttpStatus> updateManager(@PathVariable("id") Long id, @RequestBody RestaurantManagerDto restaurantManagerDto) {
        service.updateRestaurantManager(id, restaurantManagerDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteManager(@PathVariable("id") Long id) {
        service.deleteRestaurantManager(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve-manager/{restManagerId}")
    public ResponseEntity<?> approveManager(@PathVariable("restManagerId") Long restManagerId) {
        service.approveManager(restManagerId);
        return ResponseEntity.ok("Manager approved");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/rejected-manager/{restManagerId}")
    public ResponseEntity<?> rejectedManager(@PathVariable("restManagerId") Long restManagerId) {
        service.rejectedManager(restManagerId);
        return ResponseEntity.ok("Manager rejected");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending-managers")
    public ResponseEntity<List<RestaurantManagerDto>> getPendingManagers() {
        return new ResponseEntity<>(service.getAllPendingManagers(), HttpStatus.OK);
    }

    @Override
    protected User.Role getExpectedRole() {
        return User.Role.RESTAURANT_MANAGER;
    }
}
