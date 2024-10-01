package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {


    private final RestaurantService restaurantService;

    public RestaurantController (RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<RestaurantDto> restaurantDtos = restaurantService.getAllRestaurants(pageable)
                .stream()
                .collect(Collectors.toList());;
        return ResponseEntity.ok(restaurantDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantDto>> searchRestaurantsByName(
            @RequestBody String name) {
        List<RestaurantDto> restaurantDtos = restaurantService.searchRestaurantsByName(name);
        return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("id") Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateRestaurant(@PathVariable("id") Long id, @RequestBody RestaurantDto restaurantDto) {
        restaurantService.updateRestaurant(id, restaurantDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable("id") Long id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}