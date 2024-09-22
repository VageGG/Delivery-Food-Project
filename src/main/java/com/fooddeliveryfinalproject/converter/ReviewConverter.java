package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverter implements Converter<Review, ReviewDto> {

    private final CustomerConverter customerConverter;

    private final RestaurantConverter restaurantConverter;

    private final DriverConverter driverConverter;

    public ReviewConverter(CustomerConverter customerConverter,
                           RestaurantConverter restaurantConverter,
                           DriverConverter driverConverter) {
        this.customerConverter = customerConverter;
        this.restaurantConverter = restaurantConverter;
        this.driverConverter = driverConverter;
    }
    @Override
    public Review convertToEntity(ReviewDto model, Review entity) {
        entity.setReviewId(model.getReviewId());
        entity.setRating(model.getRating());
        entity.setComment(model.getComment());
        return entity;
    }

    @Override
    public ReviewDto convertToModel(Review entity, ReviewDto model) {
        model.setReviewId(entity.getReviewId());
        model.setRating(entity.getRating());
        model.setComment(entity.getComment());
        return model;
    }
}
