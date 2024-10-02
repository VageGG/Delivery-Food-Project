package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DeliveryConverter implements Converter<Delivery, DeliveryDto> {

    @Autowired
    @Lazy
    private OrderConverter orderConverter;

    @Autowired
    @Lazy
    private AddressConverter addressConverter;

    @Autowired
    private DriverConverter driverConverter;

    @Override
    public Delivery convertToEntity(DeliveryDto model, Delivery entity) {
        entity.setDeliveryId(model.getId());

        if (model.getOrderDto() != null) {
            entity.setOrder(orderConverter.convertToEntity(model.getOrderDto(), new Order()));
        }

        if (model.getDriverDto() != null) {
            entity.setDriver(driverConverter.convertToEntity(model.getDriverDto(), new Driver()));
        }

        entity.setTrackingNumber(model.getTrackingNumber());
        entity.setStatus(model.getStatus());

        if (model.getPickupLocation() != null) {
            entity.setPickupLocation(addressConverter.convertToEntity(model.getPickupLocation(), new Address()));
        }

        if (model.getDropoffLocation() != null) {
            entity.setDropoffLocation(addressConverter.convertToEntity(model.getDropoffLocation(), new Address()));
        }

        entity.setOrderTime(model.getOrderTime());

        entity.setPickupTime(model.getPickupTime());

        entity.setDropoffTime(model.getDropoffTime());

        return entity;
    }

    @Override
    public DeliveryDto convertToModel(Delivery entity, DeliveryDto model) {
        model.setId(entity.getDeliveryId());

        if (entity.getOrder() != null) {
            model.setOrderDto(orderConverter.convertToModel(entity.getOrder(), new OrderDto()));
        }

        if (entity.getDriver() != null) {
            model.setDriverDto(driverConverter.convertToModel(entity.getDriver(), new DriverDto()));
        }

        model.setTrackingNumber(entity.getTrackingNumber());
        model.setStatus(entity.getStatus());

        if (entity.getPickupLocation() != null) {
            model.setPickupLocation(addressConverter.convertToModel(entity.getPickupLocation(), new AddressDto()));
        }

        if (entity.getDropoffLocation() != null) {
            model.setDropoffLocation(addressConverter.convertToModel(entity.getDropoffLocation(), new AddressDto()));
        }

        model.setOrderTime(entity.getOrderTime());

        model.setPickupTime(entity.getPickupTime());

        model.setDropoffTime(entity.getDropoffTime());

        return model;
    }
}
