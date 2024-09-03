package com.fooddeliveryfinalproject.service;


import com.fooddeliveryfinalproject.converter.DriverConverter;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.repository.DriverRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepo driverRepo;

    private final DriverConverter driverConverter;

    public DriverService(DriverRepo driverRepo, DriverConverter driverConverter) {
        this.driverRepo = driverRepo;
        this.driverConverter = driverConverter;
    }

    @Transactional
    public List<DriverDto> getAllDrivers() {
        return driverRepo.findAll().stream()
                .map(drivers -> driverConverter.convertToModel(drivers, new DriverDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public DriverDto getDriverById(Long id) {
        return driverConverter.convertToModel(driverRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find driver")),
                new DriverDto());
    }

    @Transactional
    public void createDriver(DriverDto driverDto) {
        Driver driver = driverConverter.convertToEntity(driverDto, new Driver());
        driverRepo.save(driver);
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

