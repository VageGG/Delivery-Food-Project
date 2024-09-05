package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverConverter implements Converter<Driver, DriverDto> {

    @Autowired
    @Lazy
    private DeliveryConverter deliveryConverter;


    @Override
    public Driver convertToEntity(DriverDto model, Driver entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());

        if (model.getDeliveriesDto() != null) {
            List<Delivery> deliveryList = deliveryConverter.convertToEntityList(model.getDeliveriesDto(), Delivery::new);
            entity.setDeliveries(deliveryList);
        }
        return entity;
    }

    @Override
    public DriverDto convertToModel(Driver entity, DriverDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());

        if (entity.getDeliveries() != null) {
            List<DeliveryDto> deliveryDtoList = deliveryConverter.convertToModelList(entity.getDeliveries(), DeliveryDto::new);
            model.setDeliveriesDto(deliveryDtoList);
        }
        return model;
    }

}
