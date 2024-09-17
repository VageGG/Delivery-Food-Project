package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.repository.AddressRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepo addressRepo;

    private final AddressConverter addressConverter;

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
    public AddressDto getAddress(Long id) {
        return addressConverter.convertToModel(addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found")),
                new AddressDto());
    }

    @Transactional
    public Address createAddress(AddressDto addressDto) {
        Address address = addressConverter.convertToEntity(addressDto, new Address());
        return addressRepo.save(address);
    }

    @Transactional
    public void updateAddress(Long id, AddressDto addressDto) {
        Address addressEntity = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressConverter.convertToEntity(addressDto, addressEntity);
        addressRepo.save(addressEntity);
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepo.deleteById(id);
    }
}
