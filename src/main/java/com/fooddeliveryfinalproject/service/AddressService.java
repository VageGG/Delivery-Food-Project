package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.repository.AddressRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private AddressRepo addressRepo;

    private AddressConverter addressConverter;

    public AddressService(AddressRepo addressRepo, AddressConverter addressConverter) {
        this.addressRepo = addressRepo;
        this.addressConverter = addressConverter;
    }

    public AddressDto getAddress(Long id) {
        return addressConverter.convertToModel(addressRepo.findById(id).orElse(null), new AddressDto());
    }

    @Transactional
    public Address createAddress(Address address) {
        return addressRepo.save(address);
    }

}
