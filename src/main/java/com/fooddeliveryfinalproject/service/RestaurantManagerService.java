package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestManagerConverter;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantManagerService {

    private RestaurantManagerRepo restaurantManagerRepo;

    private RestManagerConverter restManagerConverter;

    public RestaurantManagerService(RestaurantManagerRepo restaurantManagerRepo, RestManagerConverter restManagerConverter) {
        this.restaurantManagerRepo = restaurantManagerRepo;
        this.restManagerConverter = restManagerConverter;
    }

    public RestaurantManagerDto getRestaurantManager(Long id) {
        return restManagerConverter.convertToModel(restaurantManagerRepo.findById(id).orElse(null), new RestaurantManagerDto());
    }



}
