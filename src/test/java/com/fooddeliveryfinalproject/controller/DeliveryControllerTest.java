package com.fooddeliveryfinalproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddeliveryfinalproject.config.JWTRequestFilter;
import com.fooddeliveryfinalproject.converter.DeliveryConverter;
import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.BlacklistService;
import com.fooddeliveryfinalproject.service.DeliveryService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(DeliveryController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
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

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JWTRequestFilter jwtRequestFilter;


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

        ResultActions response = mockMvc.perform(get("/delivery/list")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("DRIVER"))
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

        ResultActions response = mockMvc.perform(get("/delivery/current")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("DRIVER"))
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
        DeliveryStatusDto deliveryStatusDto = new DeliveryStatusDto();
        deliveryStatusDto.setStatus(Delivery.DeliveryStatus.DELIVERING);

        when(deliveryConverter.convertToModel(delivery, new DeliveryDto())).thenReturn(deliveryDto);

        when(deliveryService.updateDeliveryStatus(
                deliveryDto.getId(),
                Delivery.DeliveryStatus.DELIVERING,
                new Driver()
            )
        ).thenReturn(delivery);


        ResultActions response = mockMvc.perform(put("/delivery/1/update")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("DRIVER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(deliveryStatusDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
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
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("DRIVER"))
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
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("CUSTOMER"))
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
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("CUSTOMER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}