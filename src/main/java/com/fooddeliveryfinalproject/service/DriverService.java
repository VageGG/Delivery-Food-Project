package com.fooddeliveryfinalproject.service;


import com.fooddeliveryfinalproject.converter.DriverConverter;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.repository.DriverRepo;
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
public class DriverService implements ValidUser<DriverDto> {

    private final DriverRepo driverRepo;

    private final DriverConverter driverConverter;

    @Autowired
    public DriverService(DriverRepo driverRepo, DriverConverter driverConverter) {
        this.driverRepo = driverRepo;
        this.driverConverter = driverConverter;
    }

    @Transactional(readOnly = true)
    public Page<DriverDto> getAllDriver(Pageable pageable) {
        return driverRepo.findAll(pageable).map(driver ->
                driverConverter.convertToModel(driver, new DriverDto())
        );
    }

    @Transactional(readOnly = true)
    public DriverDto getDriverById(@Min(1) Long id) {
        return driverConverter.convertToModel(driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find driver")),
                new DriverDto());
    }

    @Override
    @Transactional
    public void addUser(DriverDto driverDto) throws NoSuchAlgorithmException {
        Optional<Driver> existingUser = driverRepo.findByUsername(driverDto.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username has already been used");
        }

        if (!driverDto.getRole().equals(User.Role.DRIVER)) {
            throw new RuntimeException("Role mismatch");
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

        driverDto.setStatus(RegistrationStatus.PENDING);

        driverRepo.save(driverConverter.convertToEntity(driverDto, new Driver()));
    }

    @Transactional
    public void updateDriver(@Min(1) Long id, @Valid DriverDto driverDto) {
        Driver driverEntity = driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find driver"));
        driverEntity.setUsername(driverDto.getUsername());

        if (isEmailValid(driverDto.getEmail())) {
            driverEntity.setEmail(driverDto.getEmail());
        }

        if (!isPasswordValid(driverDto.getPassword())) {
            String pw_hash = BCrypt.hashpw(driverDto.getPassword(), BCrypt.gensalt(12));
            driverEntity.setPassword(pw_hash);
        }

        driverEntity.setPhoneNumber(driverDto.getPhoneNumber());
        driverEntity.setRole(driverDto.getRole());
        driverEntity.setStatus(driverDto.getStatus());
        driverRepo.save(driverEntity);
    }

    @Transactional
    public void approveDriver(@Min(1) Long driverId) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        driver.setStatus(RegistrationStatus.APPROVED);
        driverRepo.save(driver);
    }

    @Transactional
    public void rejectedDriver(@Min(1) Long driverId) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found"));
        driver.setStatus(RegistrationStatus.REJECTED);
        driverRepo.save(driver);
    }

    @Transactional(readOnly = true)
    public List<DriverDto> getAllPendingDrivers() {
        return driverRepo.findAllByStatus(RegistrationStatus.PENDING).stream()
                .map(driver -> driverConverter.convertToModel(driver, new DriverDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDriver(Long id) {
        driverRepo.deleteById(id);
    }

}

