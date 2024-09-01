package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.model.DriverDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverConverter implements Converter<Driver, DriverDto> {

    private final DeliveryConverter deliveryConverter;

    public DriverConverter(DeliveryConverter deliveryConverter) {
        this.deliveryConverter = deliveryConverter;
    }

    @Override
    public Driver convertToEntity(DriverDto model, Driver entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());

        if(model.getDeliveries() != null) {
            List <Delivery> deliveryList = new ArrayList<>();
            for(DeliveryDto deliveryDto : model.getDeliveries()) {
                deliveryList.add(deliveryConverter.convertToEntity(deliveryDto, new Delivery()));
            }
            entity.setDeliveryList(deliveryList);
        }
        return entity;
    }

    @Override
    public DriverDto convertToModel(Driver entity, DriverDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());

        if(entity.getDeliveries()!=null){
         List <DeliveryDto> deliveryDtoList = new ArrayList<>();
         for(Delivery delivery : entity.getDeliveries()){
             deliveryDtoList.add(deliveryConverter.convertToModel(delivery, new DeliveryDto()));
         }
         model.setDeliveries(deliveryDtoList);
        }
        return model;
    }

}
