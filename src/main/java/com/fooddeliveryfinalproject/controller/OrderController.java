package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.service.OrderService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;

    private final CustomerRepo customerRepo;

    private final CartRepo cartRepo;

    @Autowired
    public OrderController(OrderService orderService,
                           OrderConverter orderConverter,
                           CustomerRepo customerRepo,
                           CartRepo cartRepo) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.customerRepo = customerRepo;
        this.cartRepo = cartRepo;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderDto createOrder(@RequestParam("cartId") @Min(1) Long cartId,
                                @RequestParam("customerId") @Min(1) Long customerId,
                                @RequestParam("restaurantBranchId") @Min(1) Long restaurantBranchId,
                                @RequestBody @NotNull AddressDto addressDto) {
        Order order = new Order();
        order.setCustomer(customerRepo.findById(customerId).get());
        order.getCustomer().setCart(cartRepo.findById(cartId).get());
        return orderConverter.convertToModel(orderService.createOrder(order, addressDto, restaurantBranchId), new OrderDto());
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public Page<OrderDto> getOrderList(@RequestParam @Min(1) Long customerId) { // ask how to get customerId
        Pageable pageable = PageRequest.of (
                0,
                2
        );
        return orderService.getOrdersByCustomer(customerId, pageable);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderDto getOrderById(@PathVariable @Min(1) Long orderId) {
        return orderConverter.convertToModel(orderService.getOrderById(orderId), new OrderDto());
    }

    @GetMapping("/pending/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public List<OrderDto> getPendingOrdersList() {
        return orderService.getPendingOrdersList();
    }

    @PostMapping("/{orderId}/take")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('DRIVER')")
    public OrderDto takeOrder(@PathVariable @Min(1) Long orderId) {
        return orderConverter.convertToModel(orderService.takeOrder(orderId), new OrderDto());
    }
}
