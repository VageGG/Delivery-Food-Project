package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.repository.DeliveryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepo repo;

    @Autowired
    private DeliveryConverter deliveryConverter;

    public List<DeliveryDto> getDeliveryList(Long driverId) {
        List<DeliveryDto> deliveries = repo.findByDriverId(driverId).stream()
                .map(delivery -> deliveryConverter.convertToModel(delivery, new DeliveryDto()))
                .collect(Collectors.toList());

        return deliveries;
    }

    public DeliveryDto getCurrentDelivery(Long driverId) {
        Delivery.DeliveryStatus[] deliveryStatuses = {
                Delivery.DeliveryStatus.PICKED_UP,
                Delivery.DeliveryStatus.DELIVERING
        };

        List<DeliveryDto> list = getDeliveryList(driverId).stream()
                .filter(deliveryDto ->
                        (
                                deliveryDto.getStatus()
                                        .equals(deliveryStatuses[0]) ||
                                deliveryDto.getStatus()
                                        .equals(deliveryStatuses[1])
                        ) &&
                            deliveryDto.getDriverDto().getId().equals(driverId)
                )
                .collect(Collectors.toList());

        return list.get(0);
    }

    @Transactional
    public Delivery updateDeliveryStatus(Long deliveryId, Delivery.DeliveryStatus deliveryStatus) {
        Delivery delivery = repo.findById(deliveryId).get();

        if (delivery == null) {
            throw new NullPointerException("delivery not found");
        }

        delivery.setStatus(deliveryStatus);
        return repo.save(delivery);
    }

    public Delivery getDeliveryById(Long deliveryId) {
        return this.repo.findById(deliveryId).orElseThrow(() ->
                    new NullPointerException("delivery not found")
        );
    }

    public Delivery getDeliveryByTrackingId(String trackingId) {
        Delivery delivery = repo.findByTrackingNumber(trackingId);

        if (delivery == null) {
            throw new NullPointerException("delivery not found");
        }

        return delivery;
    }
}
