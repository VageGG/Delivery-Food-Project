package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestaurantConverter;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;;

    private final RestaurantConverter restaurantConverter;


    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo, RestaurantConverter restaurantConverter) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantConverter = restaurantConverter;
    }


    @Transactional(readOnly = true)
    public RestaurantDto getRestaurantById(Long id) {
        return restaurantConverter.convertToModel(restaurantRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Could not find Restaurant")),
                new RestaurantDto());
    }


    @Transactional(readOnly = true)
    public Page<RestaurantDto> getAllRestaurants(Pageable pageable) {
        return restaurantRepo.findAll(pageable).map(restaurants ->
                restaurantConverter.convertToModel(restaurants, new RestaurantDto()));
    }

    @Transactional
    public List<RestaurantDto> searchRestaurantsByName(String name) {

        List<Restaurant> restaurants = restaurantRepo.findByNameContainingIgnoreCase(name);
        return restaurantConverter.convertToModelList(restaurants,RestaurantDto::new);
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
