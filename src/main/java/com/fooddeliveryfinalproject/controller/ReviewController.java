package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.Status;
import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.IdDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService service;
    private ReviewConverter converter;

    @PostMapping(value = "/review/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public IdDto createReview(@RequestBody ReviewDto reviewDto) {
        return new IdDto(this.service.createReview(this.converter.convertToEntity(reviewDto, new Review())).getReviewId());
    }

    @GetMapping(value = "/review/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReviewDto getReviewById(@PathVariable long id) {
        return this.converter.convertToModel(this.service.getReviewById(id), new ReviewDto());
    }

    @PutMapping("/review/update")
    public String updateReview(@RequestBody ReviewDto reviewDto) {
        this.service.updateReview(this.converter.convertToEntity(reviewDto, new Review()));
        return "status: " + Status.UPDATED;
    }

    @DeleteMapping("review/delete/{id}")
    public String deleteReview(@PathVariable long id) {
        this.service.deleteReview(id);
        return "status: " + Status.DELETED;
    }
}
