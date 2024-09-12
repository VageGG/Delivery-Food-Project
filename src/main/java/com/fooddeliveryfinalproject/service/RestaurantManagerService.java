package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestManagerConverter;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantManagerService {

    private final RestaurantManagerRepo restaurantManagerRepo;

    private final RestManagerConverter restManagerConverter;

    @Autowired
    public RestaurantManagerService(RestaurantManagerRepo restaurantManagerRepo, RestManagerConverter restManagerConverter) {
        this.restaurantManagerRepo = restaurantManagerRepo;
        this.restManagerConverter = restManagerConverter;
    }

    public List<RestaurantManagerDto> getAllManagers() {
        return restaurantManagerRepo.findAll().stream()
                .map(restaurantManager -> restManagerConverter.convertToModel(restaurantManager,
                        new RestaurantManagerDto()))
                .collect(Collectors.toList());
    }

    public RestaurantManagerDto getRestaurantManager(Long id) {
        return restManagerConverter.convertToModel(restaurantManagerRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Manager not found")),
                new RestaurantManagerDto());
    }

    @Transactional
    public void createRestaurantManager(RestaurantManagerDto restaurantManagerDto) {
        RestaurantManager restaurantManager = restManagerConverter.convertToEntity(restaurantManagerDto,
                new RestaurantManager());
        restaurantManagerRepo.save(restaurantManager);
    }

    @Transactional
    public void updateRestaurantManager(Long id, RestaurantManagerDto restaurantManagerDto) {
        RestaurantManager restaurantManager = restaurantManagerRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("Manager not found"));
        restManagerConverter.convertToEntity(restaurantManagerDto, restaurantManager);
        restaurantManagerRepo.save(restaurantManager);
    }

    @Transactional
    public void deleteRestaurantManager(Long id) {
        restaurantManagerRepo.deleteById(id);
    }

}
