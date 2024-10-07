package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.AllUserDto;
import com.fooddeliveryfinalproject.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@Validated
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DRIVER', 'RESTAURANT_MANAGER', 'ADMIN')")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getProfile(user.getUsername()));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DRIVER', 'RESTAURANT_MANAGER', 'ADMIN')")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid AllUserDto userDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.updateProfile(user.getUsername(), userDto);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @GetMapping("/address/list")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Address>> getAddressList(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getCustomerAddressesList(user.getUsername()));
    }

    @GetMapping("/address/{addressId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getAddressById(@PathVariable @Min(1) Long addressId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getAddress(user.getUsername(), addressId));
    }

    @PostMapping("/address")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addAddress(@RequestBody @Valid AddressDto addressDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.addAddress(user.getUsername(), addressDto));
    }

    @PutMapping("/address/{addressId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updateAddress(@PathVariable @Min(1) Long addressId, @RequestBody @Valid AddressDto addressDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.updateAddress(user.getUsername(), addressId, addressDto);
        return ResponseEntity.ok("Address updated successfully");
    }

    @DeleteMapping("/address/{addressId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deleteAddress(@PathVariable @Min(1) Long addressId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.deleteAddress(user.getUsername(), addressId);
        return ResponseEntity.ok("Address deleted successfully");
    }
}
