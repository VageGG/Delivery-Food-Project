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

    @Autowired
    private CustomerAddressConvertor customerAddressConvertor;

    @Autowired
    private RestaurantBranchConverter restaurantBranchConverter;

    @Autowired
    private DeliveryConverter deliveryConverter;
    @Override
    public Address convertToEntity(AddressDto model, Address entity) {
        entity.setId(model.getId());
        entity.setCountry(model.getCountry());
        entity.setCity(model.getCity());
        entity.setState(model.getState());
        entity.setStreet(model.getStreet());
        entity.setHouseNumber(model.getHouseNumber());
        entity.setApartmentNumber(model.getApartmentNumber());

        if (model.getCustomerAddressDtoList() != null) {
            List<CustomerAddress> customerAddressDtos = customerAddressConvertor
                    .convertToEntityList(model.getCustomerAddressDtoList(),
                    CustomerAddress::new);
            entity.setCustomerAddresses(customerAddressDtos);
        }

        if (model.getRestaurantBranchDto() != null) {
            entity.setRestaurantBranch(restaurantBranchConverter.convertToEntity(model.getRestaurantBranchDto(), new RestaurantBranch()));
        }

        if (model.getPickupLocationDto()!= null) {
            List<Delivery> deliveryDtos = deliveryConverter
                   .convertToEntityList(model.getPickupLocationDto(), Delivery::new);
            entity.setPickupLocation(deliveryDtos);
        }

        if (model.getDropoffLocationDto() != null) {
            List<Delivery> deliveryDtos = deliveryConverter
                   .convertToEntityList(model.getDropoffLocationDto(), Delivery::new);
            entity.setDropoffLocation(deliveryDtos);
        }

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

        if (entity.getCustomerAddresses()!= null) {
            model.setCustomerAddressDtoList(customerAddressConvertor
                   .convertToModelList(entity.getCustomerAddresses(), CustomerAddressDto::new));
        }

        if (entity.getRestaurantBranch()!= null) {
            model.setRestaurantBranchDto(restaurantBranchConverter
                   .convertToModel(entity.getRestaurantBranch(), new RestaurantBranchDto()));
        }

        if (entity.getPickupLocation()!= null) {
            model.setPickupLocationDto(deliveryConverter
                   .convertToModelList(entity.getPickupLocation(), DeliveryDto::new));
        }

        if (entity.getDropoffLocation()!= null) {
            model.setDropoffLocationDto(deliveryConverter
                   .convertToModelList(entity.getDropoffLocation(), DeliveryDto::new));
        }

        return model;
    }
}
