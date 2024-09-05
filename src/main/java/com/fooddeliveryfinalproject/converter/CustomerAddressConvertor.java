package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.model.CustomerAddressDto;
import com.fooddeliveryfinalproject.repository.AddressRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressConvertor implements Converter<CustomerAddress, CustomerAddressDto> {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AddressRepo addressRepo;
    @Override
    public CustomerAddress convertToEntity(CustomerAddressDto model, CustomerAddress entity) {
        entity.setCustomer(customerRepo.findById(model.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Not found customer")));

        entity.setAddress(addressRepo.findById(model.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException("Not found address")));

        return entity;
    }

    @Override
    public CustomerAddressDto convertToModel(CustomerAddress entity, CustomerAddressDto model) {
        model.setCustomerId(entity.getCustomer().getId());
        model.setAddressId(entity.getAddress().getId());
        return model;
    }
}
