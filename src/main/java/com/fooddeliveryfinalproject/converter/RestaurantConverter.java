package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.entity.Review;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.model.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    private RestaurantBranchConverter restaurantBranchConverter;

    private RestManagerConverter restManagerConverter;

    private ReviewConverter reviewConverter;

    @Autowired
    @Lazy
    public void setReviewConverter(ReviewConverter reviewConverter) {
        this.reviewConverter = reviewConverter;
    }

    @Autowired
    public void setRestManagerConverter(RestManagerConverter restManagerConverter) {
        this.restManagerConverter = restManagerConverter;
    }

    @Autowired
    @Lazy
    public void setRestaurantBranchConverter(RestaurantBranchConverter restaurantBranchConverter) {
        this.restaurantBranchConverter = restaurantBranchConverter;
    }

    @Override
    public Restaurant convertToEntity(RestaurantDto model, Restaurant entity) {
        entity.setRestId(model.getRestId());
        entity.setName(model.getName());

        if (model.getRestaurantManagerDto() != null) {
            entity.setRestaurantManager(restManagerConverter.convertToEntity(model.getRestaurantManagerDto(),
                    new RestaurantManager()));
        }

        if (model.getBranchesDto() != null) {
            List<RestaurantBranch> restaurantBranches = restaurantBranchConverter.convertToEntityList(model.getBranchesDto(), RestaurantBranch::new);
            entity.setBranches(restaurantBranches);
        }

        if (model.getReviewDtos() != null) {
            entity.setReviews(reviewConverter.convertToEntityList(model.getReviewDtos(), Review::new));
        }

        return entity;
    }

    @Override
    public RestaurantDto convertToModel(Restaurant entity, RestaurantDto model) {
        model.setRestId(entity.getRestId());
        model.setName(entity.getName());

        if (entity.getRestaurantManager() != null) {
            model.setRestaurantManagerDto(restManagerConverter.convertToModel(entity.getRestaurantManager(),
                    new RestaurantManagerDto()));
        }

        if (entity.getBranches() != null) {
            List<RestaurantBranchDto> restaurantBranchDtos = restaurantBranchConverter.convertToModelList(entity.getBranches(), RestaurantBranchDto::new);
            model.setBranchesDto(restaurantBranchDtos);
        }

        if (entity.getReviews() != null) {
            List<ReviewDto> reviewDtos = reviewConverter.convertToModelList(entity.getReviews(), ReviewDto::new);
            model.setReviewDtos(reviewDtos);
        }

        return model;
    }

}
