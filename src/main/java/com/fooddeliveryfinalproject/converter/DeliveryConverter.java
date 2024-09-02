package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class DeliveryConverter implements Converter<Delivery, DeliveryDto> {

    private final OrderConverter orderConverter;

    private final DriverConverter driverConverter;

    public DeliveryConverter(OrderConverter orderConverter, DriverConverter driverConverter) {
        this.orderConverter = orderConverter;
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
        entity.setDeliveryLocation(model.getDeliveryLocation());
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
        model.setDeliveryLocation(entity.getDeliveryLocation());
        model.setDateTime(entity.getDateTime());
        return model;
    }
}
