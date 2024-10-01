package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.service.DeliveryService;
import com.fooddeliveryfinalproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryConverter deliveryConverter;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewConverter reviewConverter;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public List<DeliveryDto> getDeliveryList(@RequestParam Long driverId) {
        return deliveryService.getDeliveryList(driverId);
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public DeliveryDto getCurrentDelivery(@RequestParam Long driverId) {
        return deliveryService.getCurrentDelivery(driverId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('DRIVER')")
    public DeliveryDto updateDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryConverter.convertToModel (
                deliveryService.updateDeliveryStatus(deliveryDto.getId(), deliveryDto.getStatus()),
                new DeliveryDto()
        );
    }

    @GetMapping("/{deliveryId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public DeliveryDto getDeliveryById(@PathVariable Long deliveryId) {
        return deliveryConverter.convertToModel(deliveryService.getDeliveryById(deliveryId), new DeliveryDto());
    }

    @GetMapping("/trackingId/{trackingId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public DeliveryDto getDeliveryByTrackingId(@PathVariable String trackingId) {
        return deliveryConverter.convertToModel(deliveryService.getDeliveryByTrackingId(trackingId), new DeliveryDto());
    }

    @PostMapping("/feedback")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewConverter.convertToEntity(reviewDto, new Review());
        return reviewConverter.convertToModel (
                reviewService.createReview(review),
                new ReviewDto()
        );
    }
}