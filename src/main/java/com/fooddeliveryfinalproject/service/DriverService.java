package com.fooddeliveryfinalproject.service;


import com.fooddeliveryfinalproject.converter.DriverConverter;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.repository.DriverRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService implements ValidUser<DriverDto> {

    private final DriverRepo driverRepo;

    private final DriverConverter driverConverter;

    public DriverService(DriverRepo driverRepo, DriverConverter driverConverter) {
        this.driverRepo = driverRepo;
        this.driverConverter = driverConverter;
    }

    public List<DriverDto> getAllDrivers() {
        return driverRepo.findAll().stream()
                .map(drivers -> driverConverter.convertToModel(drivers, new DriverDto()))
                .collect(Collectors.toList());
    }

    public DriverDto getDriverById(Long id) {
        return driverConverter.convertToModel(driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find driver")),
                new DriverDto());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void addUser(DriverDto driverDto) throws NoSuchAlgorithmException {
        Optional<Driver> existingUser = driverRepo.findByEmail(driverDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email has already been used");
        }

        if (!isEmailValid(driverDto.getEmail())) {
            throw new RuntimeException("email is invalid");
        }

        if (!isPasswordValid(driverDto.getPassword())) {
            throw new RuntimeException("password is invalid");
        }

        if (driverDto.getUsername() == null) {
            throw new RuntimeException("name must be specified");
        }
        String pw_hash = BCrypt.hashpw(driverDto.getPassword(), BCrypt.gensalt(12));
        driverDto.setPassword(pw_hash);

        driverRepo.save(driverConverter.convertToEntity(driverDto, new Driver()));
    }

    @Transactional
    public void updateDriver(Long id, DriverDto driverDto) {
        Driver driverEntity = driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find driver"));
        driverConverter.convertToEntity(driverDto, driverEntity);
        driverRepo.save(driverEntity);
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepo.deleteById(id);
    }

}

