package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.repository.DeliveryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DeliveryServiceTest {
    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepo deliveryRepo;

    @Mock
    private DeliveryConverter deliveryConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDeliveryList() {
        // given
        List<Delivery> deliveries = new ArrayList<>();

        Delivery delivery1 = new Delivery();
        Driver driver = new Driver();
        driver.setId(1L);
        delivery1.setDriver(driver);
        deliveries.add(delivery1);

        Delivery delivery2 = new Delivery();
        driver.setId(1L);
        delivery2.setDriver(driver);
        deliveries.add(delivery2);

        List<DeliveryDto> deliveryDtos = new ArrayList<>();

        DeliveryDto deliveryDto1 = new DeliveryDto();
        DriverDto driverDto = new DriverDto();
        driverDto.setId(1L);
        deliveryDto1.setDriverDto(driverDto);
        deliveryDtos.add(deliveryDto1);

        DeliveryDto deliveryDto2 = new DeliveryDto();
        deliveryDto2.setDriverDto(driverDto);
        deliveryDtos.add(deliveryDto2);

        when(deliveryRepo.findByDriverId(1L)).thenReturn(deliveries);
        when(deliveryConverter.convertToModel(
                deliveries.get(0),
                new DeliveryDto()
            )
        ).thenReturn(deliveryDto1);
        when(deliveryConverter.convertToModel(
                        deliveries.get(1),
                        new DeliveryDto()
                )
        ).thenReturn(deliveryDto2);

        // when
        List<DeliveryDto> responseDto = deliveryService.getDeliveryList(1L);

        // then
        assertEquals(deliveries.size(), responseDto.size());
    }

    @Test
    void getCurrentDelivery() {
        // given
        Long driverId = 1L;

        List<Delivery> deliveries = new ArrayList<>();

        Delivery delivery = new Delivery();
        Driver driver = new Driver();
        driver.setId(driverId);
        delivery.setDriver(driver);
        delivery.setStatus(Delivery.DeliveryStatus.DELIVERING);

        deliveries.add(delivery);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDriverDto(new DriverDto());
        deliveryDto.getDriverDto().setId(driverId);
        deliveryDto.setStatus(Delivery.DeliveryStatus.DELIVERING);

        List<DeliveryDto> deliveryDtos= new ArrayList<>();
        deliveryDtos.add(deliveryDto);

        when(deliveryRepo.findByDriverId(driverId)).thenReturn(deliveries);
        when(deliveryConverter.convertToModel(delivery, new DeliveryDto())).thenReturn(deliveryDto);

        // when
        DeliveryDto responseDto = deliveryService.getCurrentDelivery(driverId);

        // then
        assertEquals(responseDto.getId(), deliveryDtos.get(0).getId());
        assertEquals(responseDto.getStatus(), deliveryDtos.get(0).getStatus());
    }

    @Test
    void updateDeliveryStatus() {
        // given
        Long deliveryId = 1L;

        Delivery delivery = new Delivery();
        delivery.setDeliveryId(deliveryId);
        delivery.setStatus(Delivery.DeliveryStatus.PREPARING);

        Delivery savedDelivery = new Delivery();
        savedDelivery.setDeliveryId(deliveryId);
        savedDelivery.setStatus(Delivery.DeliveryStatus.PICKED_UP);

        when(deliveryRepo.findById(deliveryId)).thenReturn(Optional.of(delivery));
        when(deliveryRepo.save(savedDelivery)).thenReturn(savedDelivery);

        // when
        Delivery response = deliveryService.updateDeliveryStatus(deliveryId, Delivery.DeliveryStatus.PICKED_UP);

        // then
        assertEquals(savedDelivery.getDeliveryId(), response.getDeliveryId());
        assertEquals(savedDelivery.getStatus(), response.getStatus());
    }

    @Test
    void updateDeliveryStatusShouldThrowNullPointerException() {
        when(deliveryRepo.findById(1L)).thenReturn(null);
        assertThrows(
                NullPointerException.class,
                () -> deliveryService.updateDeliveryStatus(
                        1L,
                        Delivery.DeliveryStatus.PICKED_UP
                )
        );
    }

    @Test
    void getDeliveryById() {
        // given
        Long deliveryId = 1L;

        Delivery delivery = new Delivery();
        delivery.setDeliveryId(deliveryId);

        when(deliveryRepo.findById(deliveryId)).thenReturn(Optional.of(delivery));

        // when
        Delivery response = deliveryService.getDeliveryById(deliveryId);

        // then
        assertEquals(delivery.getDeliveryId(), response.getDeliveryId());
    }

    @Test
    void getDeliveryByIdShouldThrowNullPointerException() {
        when(deliveryRepo.findById(1L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> deliveryService.getDeliveryById(1L));
    }

    @Test
    void getDeliveryByTrackingId() {
        // given
        String trackingNumber = "abcd";

        Delivery delivery = new Delivery();
        delivery.setTrackingNumber(trackingNumber);

        when(deliveryRepo.findByTrackingNumber(trackingNumber)).thenReturn(delivery);

        // when
        Delivery response = deliveryService.getDeliveryByTrackingId(trackingNumber);

        // then
        assertEquals(delivery.getTrackingNumber(), response.getTrackingNumber());
    }

    @Test
    void getDeliveryByTrackingIdShouldThrowNullPointerException() {
        String trackingId = "abcd";
        when(deliveryRepo.findByTrackingNumber(trackingId)).thenReturn(null);
        assertThrows(
                NullPointerException.class,
                () -> deliveryService.getDeliveryByTrackingId(trackingId)
        );
    }
}