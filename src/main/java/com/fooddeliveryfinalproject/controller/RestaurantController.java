package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.service.RestaurantService;
import com.fooddeliveryfinalproject.service.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant")
@Validated
public class RestaurantController {


    private final RestaurantService restaurantService;

    private final ReviewService reviewService;

    @Autowired
    public RestaurantController (RestaurantService restaurantService,
                                 ReviewService reviewService) {
        this.restaurantService = restaurantService;
        this.reviewService = reviewService;
    }


    @GetMapping(value = "/list")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(10) @Max(100) int size) {

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
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRestaurant(@RequestBody @Valid RestaurantDto restaurantDto) {
        restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateRestaurant(@PathVariable("id") @Min(1) Long id, @RequestBody @Valid RestaurantDto restaurantDto) {
        restaurantService.updateRestaurant(id, restaurantDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurant(@PathVariable("id") @Min(1) Long id) {
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/add-review/{restaurantId}")
    public ResponseEntity<HttpStatus> addReview(@PathVariable @Min(1) Long restaurantId, @RequestBody @Valid ReviewDto reviewDto) {
        reviewService.addReview(restaurantId, reviewDto);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','RESTAURANT_MANAGER','CUSTOMER')")
    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable @Min(1) Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/update-review/{reviewId}")
    public ResponseEntity<HttpStatus> updateReview(@PathVariable @Min(1) Long reviewId, @RequestBody @Valid ReviewDto reviewDto) {
        reviewService.updateReview(reviewId, reviewDto);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/average-rating/{restaurantId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable @Min(1) Long restaurantId) {
        double averageRating = reviewService.getAverageRating(restaurantId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @GetMapping("/all-reviews/{restaurantId}")
    public ResponseEntity<List<ReviewDto>> getAllReviewsByRestaurantId(@PathVariable @Min(1) Long restaurantId) {
        List<ReviewDto> reviews = reviewService.getAllReviewsByRestaurantId(restaurantId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}