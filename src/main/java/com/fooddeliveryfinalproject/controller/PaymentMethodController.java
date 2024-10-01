package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paymentMethod")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethods(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(paymentMethodService.listPaymentMethods(user.getUsername()));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> createPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto, Authentication authentication) {
        User user = getCurrentUser(authentication);
        paymentMethodService.createPaymentMethod(user.getUsername(), paymentMethodDto);
        return ResponseEntity.ok("Payment method created successfully");
    }

    @GetMapping("/{paymentMethodId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> getPaymentMethod(@PathVariable Long paymentMethodId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(paymentMethodService.getPaymentMethods(user.getUsername(), paymentMethodId));
    }

    @PutMapping("/{paymentMethodId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> updatePaymentMethod(@PathVariable Long paymentMethodId, @RequestBody PaymentMethodDto paymentMethodDto, Authentication authentication) {
        User user = getCurrentUser(authentication);
        paymentMethodService.updatePaymentMethod(user.getUsername(), paymentMethodId, paymentMethodDto);
        return ResponseEntity.ok("Payment method updated successfully");
    }

    @DeleteMapping("/{paymentMethodId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long paymentMethodId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        paymentMethodService.deletePaymentMethod(user.getUsername(), paymentMethodId);
        return ResponseEntity.ok("Payment method deleted successfully");
    }

    private User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
