package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestManagerConverter;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantManagerService implements ValidUser<RestaurantManagerDto> {

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

    @Override
    @Transactional
    public void addUser(RestaurantManagerDto restaurantManagerDto) throws NoSuchAlgorithmException {
        Optional<RestaurantManager> existingUser = restaurantManagerRepo.findByUsername(restaurantManagerDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email has already been used");
        }

        if (!isEmailValid(restaurantManagerDto.getEmail())) {
            throw new RuntimeException("email is invalid");
        }

        if (!isPasswordValid(restaurantManagerDto.getPassword())) {
            throw new RuntimeException("password is invalid");
        }

        if (restaurantManagerDto.getUsername() == null) {
            throw new RuntimeException("name must be specified");
        }
        String pw_hash = BCrypt.hashpw(restaurantManagerDto.getPassword(), BCrypt.gensalt(12));
        restaurantManagerDto.setPassword(pw_hash);

        restaurantManagerRepo.save(restManagerConverter.convertToEntity(restaurantManagerDto, new RestaurantManager()));
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
