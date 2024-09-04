package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.Order;
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

    private final DriverConverter driverConverter;

    public DeliveryConverter(DriverConverter driverConverter) {
        this.driverConverter = driverConverter;
    }
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
        entity.setPickupLocation(model.getPickupLocation());
        entity.setDropoffLocation(model.getDeliveryLocation());
        entity.setDateTime(model.getDateTime());
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
        model.setPickupLocation(entity.getPickupLocation());
        model.setDeliveryLocation(entity.getDropoffLocation());
        model.setDateTime(entity.getDateTime());
        return model;
    }
}
