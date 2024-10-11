package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.repository.AddressRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class AddressService {

    private final AddressRepo addressRepo;

    private final AddressConverter addressConverter;

    @Autowired
    public AddressService(AddressRepo addressRepo, AddressConverter addressConverter) {
        this.addressRepo = addressRepo;
        this.addressConverter = addressConverter;
    }

    @Transactional(readOnly = true)
    public List<AddressDto> getAllAddresses() {
        return addressRepo.findAll().stream()
                .map(address -> addressConverter.convertToModel(address, new AddressDto()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AddressDto getAddress(@Min(1) Long id) {
        return addressConverter.convertToModel(addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found")),
                new AddressDto());
    }

    @Transactional
    public Address createAddress(@Valid AddressDto addressDto) {
        Address address = addressConverter.convertToEntity(addressDto, new Address());
        return addressRepo.save(address);
    }

    @Transactional
    public void updateAddress(@Min(1) Long id, @Valid AddressDto addressDto) {
        Address addressEntity = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setState(addressDto.getState());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setHouseNumber(addressDto.getHouseNumber());
        addressEntity.setApartmentNumber(addressDto.getApartmentNumber());
        addressRepo.save(addressEntity);
    }

    @Transactional
    public void deleteAddress(@Min(1) Long id) {
        addressRepo.deleteById(id);
    }
}
