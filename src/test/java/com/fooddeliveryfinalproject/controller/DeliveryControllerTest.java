package com.fooddeliveryfinalproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.BlacklistService;
import com.fooddeliveryfinalproject.service.DeliveryService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(DeliveryController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtilService jwtUtilService;

    @MockBean
    private BlacklistService blacklistService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeliveryService deliveryService;

    @MockBean
    private DeliveryConverter deliveryConverter;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewConverter reviewConverter;


    @Test
    void getDeliveryList() throws Exception {
        DriverDto driverDto = new DriverDto();
        driverDto.setId(1L);

        List<DeliveryDto> deliveryDtos = new ArrayList<>();

        DeliveryDto deliveryDto1 = new DeliveryDto();
        deliveryDto1.setDriverDto(driverDto);

        DeliveryDto deliveryDto2 = new DeliveryDto();
        deliveryDto2.setDriverDto(driverDto);

        deliveryDtos.add(deliveryDto1);
        deliveryDtos.add(deliveryDto2);

        when(deliveryService.getDeliveryList(1L)).thenReturn(deliveryDtos);

        ResultActions response = mockMvc.perform(get("/delivery/list?driverId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryDtos))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void getCurrentDelivery() throws Exception {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setDriverDto(new DriverDto());
        deliveryDto.getDriverDto().setId(1L);
        deliveryDto.setStatus(Delivery.DeliveryStatus.PICKED_UP);

        when(deliveryService.getCurrentDelivery(1L)).thenReturn(deliveryDto);

        ResultActions response = mockMvc.perform(get("/delivery/current?driverId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateDelivery() throws Exception {
        Delivery delivery = new Delivery();
        delivery.setDeliveryId(1L);
        delivery.setStatus(Delivery.DeliveryStatus.PICKED_UP);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(1L);
        deliveryDto.setStatus(Delivery.DeliveryStatus.PICKED_UP);

        when(deliveryService.updateDeliveryStatus(deliveryDto.getId(), Delivery.DeliveryStatus.PICKED_UP))
                .thenReturn(delivery);

        when(deliveryConverter.convertToModel(delivery, new DeliveryDto())).thenReturn(deliveryDto);

        ResultActions response = mockMvc.perform(put("/delivery/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void getDeliveryById() throws Exception {
        Delivery delivery = new Delivery();
        delivery.setDeliveryId(1L);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(1L);

        when(deliveryService.getDeliveryById(1L)).thenReturn(delivery);
        when(deliveryConverter.convertToModel(delivery, new DeliveryDto())).thenReturn(deliveryDto);

        ResultActions response = mockMvc.perform(get("/delivery/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryDto))
        );
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getDeliveryByTrackingId() throws Exception {
        String trackingNumber = "abcd";

        Delivery delivery = new Delivery();
        delivery.setTrackingNumber(trackingNumber);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setTrackingNumber(trackingNumber);

        when(deliveryService.getDeliveryByTrackingId(trackingNumber)).thenReturn(delivery);
        when(deliveryConverter.convertToModel(delivery, new DeliveryDto())).thenReturn(deliveryDto);

        ResultActions response = mockMvc.perform(get("/delivery/trackingId/" + trackingNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deliveryDto))
        );
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void createReview() throws Exception {
        Review review = new Review();
        review.setReviewId(1L);
        review.setCustomer(new Customer());
        review.setComment("some comment");
        review.setRating((byte) 4);
        review.setDriver(new Driver());
        review.setRestaurant(new Restaurant());

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewId(1L);
        reviewDto.setComment("some comment");
        reviewDto.setRating((byte) 4);

        when(reviewConverter.convertToEntity(reviewDto, new Review())).thenReturn(review);
        when(reviewConverter.convertToModel(reviewService.createReview(review), new ReviewDto()))
                .thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(post("/delivery/feedback")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}