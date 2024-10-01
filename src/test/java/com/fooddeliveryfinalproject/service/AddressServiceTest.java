package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.repository.AddressRepo;
import com.fooddeliveryfinalproject.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private AddressConverter addressConverter;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAddresses_ReturnsListOfAddresses() {
        // given
        Address address = new Address();
        AddressDto addressDto = new AddressDto();

        when(addressRepo.findAll()).thenReturn(Collections.singletonList(address));
        when(addressConverter.convertToModel(address, new AddressDto())).thenReturn(addressDto);

        // when
        List<AddressDto> addresses = addressService.getAllAddresses();

        // then
        assertNotNull(addresses);
        assertEquals(1, addresses.size());
        assertEquals(addressDto, addresses.get(0));
        verify(addressRepo, times(1)).findAll();
        verify(addressConverter, times(1)).convertToModel(address, new AddressDto());
    }

    @Test
    void getAddress_ReturnsAddressDto() {
        // given
        Long addressId = 1L;
        Address address = new Address();
        AddressDto addressDto = new AddressDto();

        when(addressRepo.findById(addressId)).thenReturn(Optional.of(address));
        when(addressConverter.convertToModel(address, new AddressDto())).thenReturn(addressDto);

        // when
        AddressDto result = addressService.getAddress(addressId);

        // then
        assertNotNull(result);
        assertEquals(addressDto, result);
        verify(addressRepo, times(1)).findById(addressId);
        verify(addressConverter, times(1)).convertToModel(address, new AddressDto());
    }

    @Test
    void getAddress_ThrowsRuntimeException_WhenAddressNotFound() {
        // given
        Long addressId = 1L;

        when(addressRepo.findById(addressId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> addressService.getAddress(addressId));
        assertEquals("Address not found", exception.getMessage());
        verify(addressRepo, times(1)).findById(addressId);
    }

    @Test
    void createAddress_SavesAddress() {
        // given
        AddressDto addressDto = new AddressDto();
        Address address = new Address();

        when(addressConverter.convertToEntity(addressDto, new Address())).thenReturn(address);
        when(addressRepo.save(address)).thenReturn(address);

        // when
        Address result = addressService.createAddress(addressDto);

        // then
        assertNotNull(result);
        verify(addressConverter, times(1)).convertToEntity(addressDto, new Address());
        verify(addressRepo, times(1)).save(address);
    }

    @Test
    void updateAddress_UpdatesExistingAddress() {
        // given
        Long addressId = 1L;
        AddressDto addressDto = new AddressDto();
        Address existingAddress = new Address();

        when(addressRepo.findById(addressId)).thenReturn(Optional.of(existingAddress));

        // when
        addressService.updateAddress(addressId, addressDto);

        // then
        verify(addressRepo, times(1)).findById(addressId);
        verify(addressConverter, times(1)).convertToEntity(addressDto, existingAddress);
        verify(addressRepo, times(1)).save(existingAddress);
    }

    @Test
    void updateAddress_ThrowsRuntimeException_WhenAddressNotFound() {
        // given
        Long addressId = 1L;
        AddressDto addressDto = new AddressDto();

        when(addressRepo.findById(addressId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> addressService.updateAddress(addressId, addressDto));
        assertEquals("Address not found", exception.getMessage());
        verify(addressRepo, times(1)).findById(addressId);
    }

    @Test
    void deleteAddress_DeletesAddress() {
        // given
        Long addressId = 1L;

        // when
        addressService.deleteAddress(addressId);

        // then
        verify(addressRepo, times(1)).deleteById(addressId);
    }
}

