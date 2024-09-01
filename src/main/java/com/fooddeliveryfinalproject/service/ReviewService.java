package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepo repo;

    public Review createReview(Review review) { //checks later for driver, restaurant...
        if (review.getRating() > 5 || review.getRating() < 1) {
            throw new RuntimeException("rating must be between 1 and 5");
        }

        if (review.getComment().length() > 200 || review.getComment().length() < 5) {
            throw new RuntimeException("comment must be at least 5 and less than 200 characters long");
        }
        return this.repo.save(review);
    }

    public Review getReviewById(long id) {
        Review review = this.repo.getReferenceById(id);
        if (review == null) {
            throw new RuntimeException("review not found");
        }

        return review;
    }

    public Review updateReview(Review review) {
        getReviewById(review.getReviewId());

        if (review.getComment().length() > 200 || review.getComment().length() < 5) {
            throw new RuntimeException("comment must be at least 5 and less than 200 characters long");
        }

        return this.repo.save(review);
    }

    public void deleteReview(long id) {
        Review review = getReviewById(id);
        this.repo.delete(review);
    }
}
