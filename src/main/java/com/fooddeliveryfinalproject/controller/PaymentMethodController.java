package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentMethod")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getPaymentMethods(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(paymentMethodService.getPaymentMethods(user.getId()));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        paymentMethodService.createPaymentMethod(user.getEmail(), paymentMethodDto);
        return ResponseEntity.ok("Payment method created successfully");
    }

    @GetMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> getPaymentMethod(@PathVariable Long paymentMethodId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(paymentMethodService.getPaymentMethods(user.getEmail(), paymentMethodId));
    }

    @PutMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Long paymentMethodId, @RequestBody PaymentMethodDto paymentMethodDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        paymentMethodService.updatePaymentMethod(user.getEmail(), paymentMethodId, paymentMethodDto);
        return ResponseEntity.ok("Payment method updated successfully");
    }

    @DeleteMapping("/{paymentMethodId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long paymentMethodId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        paymentMethodService.deletePaymentMethod(user.getEmail(), paymentMethodId);
        return ResponseEntity.ok("Payment method deleted successfully");
    }
}
