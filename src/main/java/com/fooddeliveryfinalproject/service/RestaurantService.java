package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestaurantConverter;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import com.fooddeliveryfinalproject.repository.RestaurantRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class RestaurantService {

    private final RestaurantRepo restaurantRepo;

    private final RestaurantConverter restaurantConverter;

    private final RestaurantManagerRepo restaurantManagerRepo;


    @Autowired
    public RestaurantService(RestaurantRepo restaurantRepo,
                             RestaurantConverter restaurantConverter,
                             RestaurantManagerRepo restaurantManagerRepo
                             ) {
        this.restaurantRepo = restaurantRepo;
        this.restaurantConverter = restaurantConverter;
        this.restaurantManagerRepo = restaurantManagerRepo;
    }


    @Transactional(readOnly = true)
    public RestaurantDto getRestaurantById(@Min(1) Long id) {
        return restaurantConverter.convertToModel(restaurantRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Could not find Restaurant")),
                new RestaurantDto());
    }

    @Transactional(readOnly = true)
    public Restaurant getRest(Long id) {
        return restaurantRepo.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Transactional(readOnly = true)
    public Page<RestaurantDto> getAllRestaurants(Pageable pageable) {
        return restaurantRepo.findAll(pageable).map(restaurants ->
                restaurantConverter.convertToModel(restaurants, new RestaurantDto()));
    }

    @Transactional
    public List<RestaurantDto> searchRestaurantsByName(@NotNull String name) {

        List<Restaurant> restaurants = restaurantRepo.findByNameContainingIgnoreCase(name);
        return restaurantConverter.convertToModelList(restaurants,RestaurantDto::new);
    }

    @Transactional
    public void createRestaurant (@Valid RestaurantDto restaurantDto){
        Restaurant restaurant = restaurantConverter.convertToEntity(restaurantDto, new Restaurant());
        restaurantRepo.save(restaurant);
    }

    @Transactional
    public void addOrChangedManagerToRestaurant(Long restId, Long managerId) {
        Restaurant restaurant = restaurantRepo.findById(restId)
                .orElseThrow(() -> new RuntimeException("Restaurants not found"));
        if (!restaurantRepo.existsRestaurantByRestaurantManagerId(managerId)) {
            RestaurantManager restaurantManager = restaurantManagerRepo.findById(managerId).get();
            restaurant.setRestaurantManager(restaurantManager);
        } else {
            throw new RuntimeException("Manager already assigned to this restaurant");
        }
        restaurantRepo.save(restaurant);
    }

    @Transactional
    public void updateRestaurant(@Min(1) Long id, @Valid RestaurantDto restaurantDto){
        Restaurant restaurantEntity = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find Restaurant"));
        restaurantEntity.setName(restaurantDto.getName());
        restaurantRepo.save(restaurantEntity);
    }

    @Transactional
    public void deleteRestaurant (@Min(1) Long id){
        restaurantRepo.deleteById(id);
    }

}
