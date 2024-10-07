package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestManagerConverter;
import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class RestaurantManagerService implements ValidUser<RestaurantManagerDto> {

    private final RestaurantManagerRepo restaurantManagerRepo;

    private final RestManagerConverter restManagerConverter;

    @Autowired
    public RestaurantManagerService(RestaurantManagerRepo restaurantManagerRepo, RestManagerConverter restManagerConverter) {
        this.restaurantManagerRepo = restaurantManagerRepo;
        this.restManagerConverter = restManagerConverter;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantManagerDto> getAllManagers(Pageable pageable) {
        return restaurantManagerRepo.findAll(pageable).map(restaurantManager ->
                restManagerConverter.convertToModel(restaurantManager, new RestaurantManagerDto())
        );
    }

    @Transactional(readOnly = true)
    public RestaurantManagerDto getRestaurantManager(@Min(1) Long id) {
        return restManagerConverter.convertToModel(restaurantManagerRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Manager not found")),
                new RestaurantManagerDto());
    }

    @Override
    @Transactional
    public void addUser(@Valid RestaurantManagerDto restaurantManagerDto) throws NoSuchAlgorithmException {
        Optional<RestaurantManager> existingUser = restaurantManagerRepo.findByUsername(restaurantManagerDto.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email has already been used");
        }

        if (!restaurantManagerDto.getRole().equals(User.Role.RESTAURANT_MANAGER)) {
            throw new RuntimeException("Role mismatch");
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

        restaurantManagerDto.setStatus(RegistrationStatus.PENDING);

        restaurantManagerRepo.save(restManagerConverter.convertToEntity(restaurantManagerDto, new RestaurantManager()));
    }

    @Transactional
    public void updateRestaurantManager(@Min(1) Long id, @Valid RestaurantManagerDto restaurantManagerDto) {
        RestaurantManager restaurantManager = restaurantManagerRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("Manager not found"));
        restaurantManager.setUsername(restaurantManagerDto.getUsername());

        if (isEmailValid(restaurantManagerDto.getEmail())) {
            restaurantManager.setEmail(restaurantManagerDto.getEmail());
        }

        if (!isPasswordValid(restaurantManagerDto.getPassword())) {
            String pw_hash = BCrypt.hashpw(restaurantManagerDto.getPassword(), BCrypt.gensalt(12));
            restaurantManager.setPassword(pw_hash);
        }

        restaurantManager.setPhoneNumber(restaurantManagerDto.getPhoneNumber());
        restaurantManager.setRole(restaurantManagerDto.getRole());
        restaurantManager.setStatus(restaurantManagerDto.getStatus());
        restaurantManagerRepo.save(restaurantManager);
    }

    @Transactional
    public void approveManager(@Min(1) Long managerId) {
        RestaurantManager restaurantManager = restaurantManagerRepo.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        restaurantManager.setStatus(RegistrationStatus.APPROVED);
        restaurantManagerRepo.save(restaurantManager);
    }

    @Transactional
    public void rejectedManager(@Min(1) Long managerId) {
        RestaurantManager restaurantManager = restaurantManagerRepo.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        restaurantManager.setStatus(RegistrationStatus.REJECTED);
        restaurantManagerRepo.save(restaurantManager);
    }

    @Transactional(readOnly = true)
    public List<RestaurantManagerDto> getAllPendingManagers() {
        return restaurantManagerRepo.findAllByStatus(RegistrationStatus.PENDING).stream()
                .map(restManager -> restManagerConverter.convertToModel(restManager, new RestaurantManagerDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRestaurantManager(@Min(1) Long id) {
        restaurantManagerRepo.deleteById(id);
    }

}
