package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.repository.RestaurantRepo;
import com.fooddeliveryfinalproject.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepo repo;

    private final RestaurantRepo restaurantRepo;

    private final ReviewConverter reviewConverter;


    @Autowired
    public ReviewService(ReviewRepo repo,
                         ReviewConverter reviewConverter,
                         RestaurantRepo restaurantRepo) {
        this.repo = repo;
        this.restaurantRepo = restaurantRepo;
        this.reviewConverter = reviewConverter;
    }

    @Transactional
    public Review createReview(Review review) {
        if (review.getRating() > 5 || review.getRating() < 1) {
            throw new RuntimeException("rating must be between 1 and 5");
        }

        if (review.getComment().length() > 200 || review.getComment().length() < 5) {
            throw new RuntimeException("comment must be at least 5 and less than 200 characters long");
        }
        return this.repo.save(review);
    }

    @Transactional(readOnly = true)
    public Review getReviewById(long id) {
        Review review = this.repo.getReferenceById(id);
        if (review == null) {
            throw new RuntimeException("review not found");
        }

        return review;
    }

    @Transactional
    public Review updateReview(Review review) {
        getReviewById(review.getReviewId());

        if (review.getComment().length() > 200 || review.getComment().length() < 5) {
            throw new RuntimeException("comment must be at least 5 and less than 200 characters long");
        }

        return this.repo.save(review);
    }

    @Transactional
    public void deleteReview(long id) {
        Review review = getReviewById(id);
        this.repo.delete(review);
    }

    @Transactional
    public void addReview(Long restaurantId, ReviewDto reviewDto) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Review review = reviewConverter.convertToEntity(reviewDto, new Review());
        review.setRestaurant(restaurant);

        repo.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        repo.deleteById(reviewId);
    }

    @Transactional
    public ReviewDto updateReview(Long reviewId, ReviewDto reviewDto) {
        Review existingReview = repo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setComment(reviewDto.getComment());
        existingReview.setRating(reviewDto.getRating());

        return reviewConverter.convertToModel(repo.save(existingReview),new ReviewDto());
    }

    @Transactional(readOnly = true)
    public double getAverageRating(Long restaurantId) {
        List<Review> reviews = repo.findByRestaurant_RestId( restaurantId);
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviewsByRestaurantId(Long restaurantId) {
        List<Review> reviews = repo.findAllByRestaurant_RestId(restaurantId);
        return reviews.stream()
                .map(review -> reviewConverter.convertToModel(review,new ReviewDto()))
                .collect(Collectors.toList());
    }
}
