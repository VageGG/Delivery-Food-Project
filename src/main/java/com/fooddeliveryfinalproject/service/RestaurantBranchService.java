package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestaurantBranchConverter;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.repository.RestaurantBranchRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantBranchService {
    private final RestaurantBranchRepo restaurantBranchRepo;

    private final RestaurantBranchConverter restaurantBranchConverter;

    @Autowired
    public RestaurantBranchService(RestaurantBranchRepo restaurantBranchRepo, RestaurantBranchConverter restaurantBranchConverter) {
        this.restaurantBranchRepo = restaurantBranchRepo;
        this.restaurantBranchConverter = restaurantBranchConverter;
    }

    public RestaurantBranchDto getRestaurantBranch(Long id) {
        return restaurantBranchConverter.convertToModel(restaurantBranchRepo.findById(id)
                        .orElseThrow(()->new RuntimeException("Could not find RestaurantBranch")),
                new RestaurantBranchDto());
    }

    public List<RestaurantBranchDto> getAllRestaurantBranches() {
        return restaurantBranchRepo.findAll().stream()
                .map(restaurantBranches -> restaurantBranchConverter.convertToModel(restaurantBranches, new RestaurantBranchDto()))
                .collect(Collectors.toList());
    }
    // addPoint get all branch by id restaurant
    public List<RestaurantBranchDto> getAllRestaurantBranches(Long id) {
        return restaurantBranchRepo.findAllByRestaurantId(id).stream()
                .map(restaurantBranches -> restaurantBranchConverter.convertToModel(restaurantBranches, new RestaurantBranchDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createRestaurantBranch (RestaurantBranchDto restaurantBranchDto){
        RestaurantBranch restaurantBranch = restaurantBranchConverter.convertToEntity(restaurantBranchDto, new RestaurantBranch());
        restaurantBranchRepo.save(restaurantBranch);
    }

    @Transactional
    public void updateRestaurantBranch(Long id, RestaurantBranchDto restaurantBranchDto){
        RestaurantBranch restaurantBranchEntity = restaurantBranchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find RestaurantBranch"));
        restaurantBranchConverter.convertToEntity(restaurantBranchDto, restaurantBranchEntity);
        restaurantBranchRepo.save(restaurantBranchEntity);

    }

    @Transactional
    public void deleteRestaurantBranch(Long id){
        restaurantBranchRepo.deleteById(id);
    }
}
