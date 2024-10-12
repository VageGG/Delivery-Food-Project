package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.repository.AddressRepo;
import com.fooddeliveryfinalproject.repository.DeliveryRepo;
import com.fooddeliveryfinalproject.repository.RestaurantBranchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    private final DeliveryRepo repo;

    private final DeliveryConverter deliveryConverter;

    private final AddressConverter addressConverter;

    private final RestaurantBranchRepo restaurantBranchRepo;

    private final AddressRepo addressRepo;

    @Autowired
    public DeliveryService(DeliveryRepo repo,
                           DeliveryConverter deliveryConverter,
                           AddressConverter addressConverter,
                           RestaurantBranchRepo restaurantBranchRepo,
                           AddressRepo addressRepo) {
        this.repo = repo;
        this.deliveryConverter = deliveryConverter;
        this.addressConverter = addressConverter;
        this.restaurantBranchRepo = restaurantBranchRepo;
        this.addressRepo = addressRepo;
    }

    @Transactional
    public Delivery createDelivery(AddressDto addressDto, Long restaurantBranchId, Order order) {
        if (addressDto == null) {
            throw new NullPointerException("drop off location must be specified");
        }

        if (addressDto.getCountry() == null) {
            throw new NullPointerException("country must be specified");
        }

        if (addressDto.getCity() == null) {
            throw new NullPointerException("city must be specified");
        }

        if (addressDto.getStreet() == null) {
            throw new NullPointerException("street must be specified");
        }

        if (addressDto.getHouseNumber() == null && addressDto.getApartmentNumber() == null) {
            throw new NullPointerException("either house number or apartment number must be specified");
        }

        RestaurantBranch restaurantBranch = restaurantBranchRepo.findById(restaurantBranchId)
                .orElseThrow(() -> new NullPointerException("pick up location must be specified"));

        Delivery delivery = new Delivery();

        delivery.setDropoffLocation(
                addressRepo.save(addressConverter.convertToEntity(
                            addressDto,
                            new Address()
                        )
                )
        );

        delivery.setTrackingNumber(UUID.randomUUID().toString());

        delivery.setPickupLocation(restaurantBranch.getAddress());

        LocalDateTime dateTime = LocalDateTime.now();
        delivery.setOrderTime(dateTime);

        delivery.setStatus(Delivery.DeliveryStatus.PREPARING);

        delivery.setOrder(order);

        return repo.save(delivery);
    }

    @Transactional(readOnly = true)
    public List<DeliveryDto> getDeliveryList(Long driverId) {
        List<DeliveryDto> deliveries = repo.findByDriverId(driverId).stream()
                .map(delivery -> deliveryConverter.convertToModel(delivery, new DeliveryDto()))
                .collect(Collectors.toList());

        return deliveries;
    }

    @Transactional(readOnly = true)
    public DeliveryDto getCurrentDelivery(Long driverId) {
        Delivery.DeliveryStatus[] deliveryStatuses = {
                Delivery.DeliveryStatus.PICKED_UP,
                Delivery.DeliveryStatus.DELIVERING
        };

        List<DeliveryDto> list = repo.findByDriverId(driverId).stream()
                .filter(delivery -> (
                                delivery.getStatus()
                                        .equals(deliveryStatuses[0]) ||
                                delivery.getStatus()
                                        .equals(deliveryStatuses[1])
                        )
                )
                .map(delivery -> deliveryConverter.convertToModel(delivery, new DeliveryDto()))
                .toList();

        if (list.size() == 0) {
            throw new RuntimeException("you currently don't have any delivery with picked up or delivering status");
        }

        return list.get(0);
    }

    @Transactional
    public Delivery updateDeliveryStatus(Long deliveryId, Delivery.DeliveryStatus deliveryStatus) {
        Delivery delivery = repo.findById(deliveryId)
                .orElseThrow(() -> new NullPointerException("delivery not found"));

        Delivery.DeliveryStatus[] deliveryStatuses = {
                Delivery.DeliveryStatus.DELIVERED,
                Delivery.DeliveryStatus.DELIVERING
        };

        if (
                    (deliveryStatus.equals(deliveryStatuses[0])) ||
                    (deliveryStatus.equals(deliveryStatuses[1]))
        ) {
            if (deliveryStatus.equals(deliveryStatuses[0])) {
                delivery.setDropoffTime(LocalDateTime.now());
            }

            else {
                delivery.setPickupTime(LocalDateTime.now());
            }

            delivery.setStatus(deliveryStatus);

            return repo.save(delivery);
        }
        throw new RuntimeException("Invalid status");
    }

    @Transactional(readOnly = true)
    public Delivery getDeliveryById(Long deliveryId) {
        return this.repo.findById(deliveryId).orElseThrow(() ->
                    new NullPointerException("delivery not found")
        );
    }

    @Transactional(readOnly = true)
    public Delivery getDeliveryByTrackingId(String trackingId) {
        Delivery delivery = repo.findByTrackingNumber(trackingId);

        if (delivery == null) {
            throw new NullPointerException("delivery not found");
        }

        return delivery;
    }
}
