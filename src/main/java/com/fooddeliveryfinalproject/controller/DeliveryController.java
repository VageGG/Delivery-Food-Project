package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.CreateReviewDto;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.DeliveryStatusDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.service.DeliveryService;
import com.fooddeliveryfinalproject.service.ReviewService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {


    private final DeliveryService deliveryService;

    private final DeliveryConverter deliveryConverter;

    private final ReviewService reviewService;

    private final ReviewConverter reviewConverter;

    @Autowired
    public DeliveryController(DeliveryService deliveryService,
                              DeliveryConverter deliveryConverter,
                              ReviewService reviewService,
                              ReviewConverter reviewConverter) {
        this.deliveryService = deliveryService;
        this.deliveryConverter = deliveryConverter;
        this.reviewService = reviewService;
        this.reviewConverter = reviewConverter;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public List<DeliveryDto> getDeliveryList(@RequestParam @Min(1) Long driverId) {
        return deliveryService.getDeliveryList(driverId);
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public DeliveryDto getCurrentDelivery(@RequestParam @Min(1) Long driverId) {
        return deliveryService.getCurrentDelivery(driverId);
    }

    @PutMapping("/{deliveryId}/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('DRIVER')")
    public DeliveryDto updateDelivery(@PathVariable @Min(1) Long deliveryId, @RequestBody DeliveryStatusDto deliveryStatusDto) {
        return deliveryConverter.convertToModel (
                deliveryService.updateDeliveryStatus(deliveryId, deliveryStatusDto.getStatus()),
                new DeliveryDto()
        );
    }

    @GetMapping("/{deliveryId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public DeliveryDto getDeliveryById(@PathVariable @Min(1) Long deliveryId) {
        return deliveryConverter.convertToModel(deliveryService.getDeliveryById(deliveryId), new DeliveryDto());
    }

    @GetMapping("/trackingId/{trackingId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public DeliveryDto getDeliveryByTrackingId(@PathVariable @NotNull String trackingId) {
        return deliveryConverter.convertToModel(deliveryService.getDeliveryByTrackingId(trackingId), new DeliveryDto());
    }

    @PostMapping("/feedback")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ReviewDto createReview(@RequestBody @NotNull CreateReviewDto reviewDto) {
        Review review =  new Review();
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setRestaurant(new Restaurant());
        review.getRestaurant().setRestId(reviewDto.getRestaurantId());
        review.setCustomer(new Customer());
        review.getCustomer().setId(reviewDto.getCustomerId());

        return reviewConverter.convertToModel (
                reviewService.createReview(review),
                new ReviewDto()
        );
    }
}