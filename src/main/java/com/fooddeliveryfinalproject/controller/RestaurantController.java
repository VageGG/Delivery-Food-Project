package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.service.RestaurantService;
import com.fooddeliveryfinalproject.service.ReviewService;
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

    private final ReviewService reviewService;

    @Autowired
    public RestaurantController (RestaurantService restaurantService,
                                 ReviewService reviewService) {
        this.restaurantService = restaurantService;
        this.reviewService = reviewService;
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

    @PostMapping("/add-review/{restaurantId}")
    public ResponseEntity<HttpStatus> addReview(@PathVariable Long restaurantId, @RequestBody ReviewDto reviewDto) {
        reviewService.addReview(restaurantId, reviewDto);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update-review/{reviewId}")
    public ResponseEntity<HttpStatus> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDto reviewDto) {
        reviewService.updateReview(reviewId, reviewDto);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/average-rating{restaurantId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long restaurantId) {
        double averageRating = reviewService.getAverageRating(restaurantId);
        return new ResponseEntity<>(averageRating, HttpStatus.OK);
    }

    @GetMapping("/all-reviews/{restaurantId}")
    public ResponseEntity<List<ReviewDto>> getAllReviewsByRestaurantId(@PathVariable Long restaurantId) {
        List<ReviewDto> reviews = reviewService.getAllReviewsByRestaurantId(restaurantId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}