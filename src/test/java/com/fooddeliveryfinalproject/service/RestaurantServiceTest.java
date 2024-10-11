package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestaurantConverter;
import com.fooddeliveryfinalproject.converter.ReviewConverter;
import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.model.RestaurantDto;
import com.fooddeliveryfinalproject.repository.RestaurantRepo;
import com.fooddeliveryfinalproject.repository.ReviewRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepo restaurantRepo;

    @Mock
    private RestaurantConverter restaurantConverter;

    @Mock
    private ReviewConverter reviewConverter;

    @Mock
    private ReviewRepo reviewRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetRestaurantById_Success() {
        // Given
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setRestId(restaurantId);
        RestaurantDto restaurantDto = new RestaurantDto();
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantConverter.convertToModel(any(Restaurant.class), any(RestaurantDto.class)))
                .thenReturn(restaurantDto);

        // When
        RestaurantDto result = restaurantService.getRestaurantById(restaurantId);

        // Then
        assertEquals(restaurantDto, result);
        verify(restaurantRepo).findById(restaurantId);
        verify(restaurantConverter).convertToModel(any(Restaurant.class), any(RestaurantDto.class));
    }

    @Test
    public void testGetRestaurantById_NotFound() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> restaurantService.getRestaurantById(restaurantId));
        verify(restaurantRepo).findById(restaurantId);
    }

    @Test
    public void testGetAllRestaurants() {
        // Given
        Pageable pageable = mock(Pageable.class);
        Restaurant restaurant = new Restaurant();
        RestaurantDto restaurantDto = new RestaurantDto();
        Page<Restaurant> restaurantPage = new PageImpl<>(Collections.singletonList(restaurant));
        when(restaurantRepo.findAll(pageable)).thenReturn(restaurantPage);
        when(restaurantConverter.convertToModel(any(Restaurant.class), any(RestaurantDto.class)))
                .thenReturn(restaurantDto);

        // When
        Page<RestaurantDto> result = restaurantService.getAllRestaurants(pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        verify(restaurantRepo).findAll(pageable);
        verify(restaurantConverter).convertToModel(any(Restaurant.class), any(RestaurantDto.class));
    }

    @Test
    public void testCreateRestaurant() {
        // Given
        RestaurantDto restaurantDto = new RestaurantDto();
        Restaurant restaurant = new Restaurant();
        when(restaurantConverter.convertToEntity(any(RestaurantDto.class), any(Restaurant.class)))
                .thenReturn(restaurant);

        // When
        restaurantService.createRestaurant(restaurantDto);

        // Then
        verify(restaurantConverter).convertToEntity(any(RestaurantDto.class), any(Restaurant.class));
        verify(restaurantRepo).save(restaurant);
    }

    @Test
    public void testUpdateRestaurant() {
        // Given
        Long restaurantId = 1L;
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName("Updated Name");
        Restaurant restaurant = new Restaurant();
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        // When
        restaurantService.updateRestaurant(restaurantId, restaurantDto);

        // Then
        verify(restaurantRepo).findById(restaurantId);
        verify(restaurantRepo).save(restaurant);
        assertEquals("Updated Name", restaurant.getName());
    }

    @Test
    public void testDeleteRestaurant() {
        // Given
        Long restaurantId = 1L;

        // When
        restaurantService.deleteRestaurant(restaurantId);

        // Then
        verify(restaurantRepo).deleteById(restaurantId);
    }
}