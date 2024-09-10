package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestaurantConverter;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.repository.RestaurantRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;;

    private final RestaurantConverter restaurantConverter;


    public RestaurantService(RestaurantRepo restaurantRepo, RestaurantConverter restaurantConverter) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantConverter = restaurantConverter;
    }


    public RestaurantDto getRestaurant(Long id) {
        return restaurantConverter.convertToModel(restaurantRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Could not find Restaurant")),
                new RestaurantDto());
    }


    public List<RestaurantDto> getAllRestaurants() {
        return restaurantRepo.findAll().stream()
                .map(restaurants -> restaurantConverter.convertToModel(restaurants, new RestaurantDto()))
                .collect(Collectors.toList());
    }
    // addPoint search
    public RestaurantDto searchRest(String name) {
        return restaurantConverter.convertToModel(restaurantRepo.findByName(name), new RestaurantDto());
    }

    @Transactional
    public void createRestaurant (RestaurantDto restaurantDto){
        Restaurant restaurant = restaurantConverter.convertToEntity(restaurantDto, new Restaurant());
        restaurantRepo.save(restaurant);
    }

    @Transactional
    public void updateRestaurant(Long id, RestaurantDto restaurantDto){
        Restaurant restaurantEntity = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find Restaurant"));
        restaurantConverter.convertToEntity(restaurantDto, restaurantEntity);
        restaurantRepo.save(restaurantEntity);

    }

    @Transactional
    public void deleteRestaurant (Long id){
        restaurantRepo.deleteById(id);
    }

}
