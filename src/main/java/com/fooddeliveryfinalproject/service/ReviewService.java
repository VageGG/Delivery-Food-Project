package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.RestaurantRepo;
import com.fooddeliveryfinalproject.repository.ReviewRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ReviewService {

    private final ReviewRepo repo;

    private final ReviewConverter reviewConverter;

    private final RestaurantRepo restaurantRepo;

    private final CustomerRepo customerRepo;

    @Autowired
    public ReviewService(ReviewRepo repo,
                         ReviewConverter reviewConverter,
                         RestaurantRepo restaurantRepo,
                         CustomerRepo customerRepo) {
        this.repo = repo;
        this.reviewConverter = reviewConverter;
        this.restaurantRepo = restaurantRepo;
        this.customerRepo = customerRepo;
    }

    @Transactional
    public Review createReview(@Valid Review review) {
        if (review.getRating() > 5 || review.getRating() < 1) {
            throw new RuntimeException("rating must be between 1 and 5");
        }

        if (review.getComment().length() > 200 || review.getComment().length() < 5) {
            throw new RuntimeException("comment must be at least 5 and less than 200 characters long");
        }
        Restaurant restaurant = restaurantRepo.findById(review.getRestaurant().getRestId())
                .orElseThrow(() -> new NullPointerException("restaurant not found"));

        review.setRestaurant(restaurant);

        Customer customer = customerRepo.findById(review.getCustomer().getId())
                .orElseThrow(() -> new NullPointerException("customer not found"));

        review.setCustomer(customer);

        return this.repo.save(review);
    }

    @Transactional(readOnly = true)
    public Review getReviewById(@Min(1) long id) {
        Review review = this.repo.getReferenceById(id);
        if (review == null) {
            throw new RuntimeException("review not found");
        }

        return review;
    }

    @Transactional
    public Review updateReview(@Valid Review review) {
        getReviewById(review.getReviewId());

        if (review.getComment().length() > 200 || review.getComment().length() < 5) {
            throw new RuntimeException("comment must be at least 5 and less than 200 characters long");
        }

        return this.repo.save(review);
    }

    @Transactional
    public void deleteReview(@Min(1) long id) {
        Review review = getReviewById(id);
        this.repo.delete(review);
    }

    @Transactional
    public void addReview(@Min(1) Long restaurantId, @Valid ReviewDto reviewDto, Customer customer) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Review review = new Review();
        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());
        review.setRestaurant(restaurant);
        review.setCustomer(customer);

        repo.save(review);
    }

    @Transactional
    public void deleteReview(@Min(1) Long reviewId) {
        repo.deleteById(reviewId);
    }

    @Transactional
    public ReviewDto updateReview(@Min(1) Long reviewId, @Valid ReviewDto reviewDto) {
        Review existingReview = repo.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        existingReview.setComment(reviewDto.getComment());
        existingReview.setRating(reviewDto.getRating());

        return reviewConverter.convertToModel(repo.save(existingReview),new ReviewDto());
    }

    @Transactional(readOnly = true)
    public double getAverageRating(@Min(1) Long restaurantId) {
        List<Review> reviews = repo.findByRestaurant_RestId(restaurantId);
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        return Math.round(averageRating * 10.0) / 10.0; // Округление до 1 знака
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviewsByRestaurantId(@Min(1) Long restaurantId) {
        List<Review> reviews = repo.findAllByRestaurant_RestId(restaurantId);
        return reviews.stream()
                .map(review -> reviewConverter.convertToModel(review,new ReviewDto()))
                .collect(Collectors.toList());
    }
}
