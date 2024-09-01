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

        if (model.getCustomerDto() != null) {
            entity.setCustomer(customerConverter.convertToEntity(model.getCustomerDto(), new Customer()));
        }

        if (model.getRestaurantDto()!= null) {
            entity.setRestaurant(restaurantConverter.convertToEntity(model.getRestaurantDto(), new Restaurant()));
        }

        if (model.getDriverDto()!= null) {
            entity.setDriver(driverConverter.convertToEntity(model.getDriverDto(), new Driver()));
        }

        entity.setRating(model.getRating());
        entity.setComment(model.getComment());
        return entity;
    }

    @Override
    public ReviewDto convertToModel(Review entity, ReviewDto model) {
        model.setReviewId(entity.getReviewId());

        if (entity.getCustomer()!= null) {
            model.setCustomerDto(customerConverter.convertToModel(entity.getCustomer(), new CustomerDto()));
        }

        if (entity.getRestaurant()!= null) {
            model.setRestaurantDto(restaurantConverter.convertToModel(entity.getRestaurant(), new RestaurantDto()));
        }

        if (entity.getDriver()!= null) {
            model.setDriverDto(driverConverter.convertToModel(entity.getDriver(), new DriverDto()));
        }

        model.setRating(entity.getRating());
        model.setComment(entity.getComment());
        return model;
    }
}
