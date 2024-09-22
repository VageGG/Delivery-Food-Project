package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerAddressDto;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressConverter implements Converter<Address, AddressDto> {

    @Override
    public Address convertToEntity(AddressDto model, Address entity) {
        entity.setId(model.getId());
        entity.setCountry(model.getCountry());
        entity.setCity(model.getCity());
        entity.setState(model.getState());
        entity.setStreet(model.getStreet());
        entity.setHouseNumber(model.getHouseNumber());
        entity.setApartmentNumber(model.getApartmentNumber());

        return entity;
    }

    @Override
    public AddressDto convertToModel(Address entity, AddressDto model) {
        model.setId(entity.getId());
        model.setCountry(entity.getCountry());
        model.setCity(entity.getCity());
        model.setState(entity.getState());
        model.setStreet(entity.getStreet());
        model.setHouseNumber(entity.getHouseNumber());
        model.setApartmentNumber(entity.getApartmentNumber());

        return model;
    }
}
