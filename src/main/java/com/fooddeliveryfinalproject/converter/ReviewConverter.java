package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.ReviewDto;

public class ReviewConverter implements Converter<Review, ReviewDto> {

    @Override
    public Review convertToEntity(ReviewDto reviewDto, Review review) {
        review.setReviewId(reviewDto.getReviewId());
        review.setCustomer(reviewDto.getCustomer());
        review.setRestaurant(reviewDto.getRestaurant());
        review.setDriver(reviewDto.getDriver());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        return review;
    }

    @Override
    public ReviewDto convertToModel(Review review, ReviewDto reviewDto) {
        reviewDto.setReviewId(review.getReviewId());
        reviewDto.setCustomer(review.getCustomer());
        reviewDto.setRestaurant(review.getRestaurant());
        reviewDto.setDriver(review.getDriver());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        return reviewDto;
    }
}
