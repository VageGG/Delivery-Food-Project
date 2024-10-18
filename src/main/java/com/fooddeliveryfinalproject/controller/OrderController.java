package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.model.PageDto;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.service.OrderService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;

    private final CartRepo cartRepo;

    @Autowired
    public OrderController(
            OrderService orderService,
            OrderConverter orderConverter,
            CartRepo cartRepo
    ) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.cartRepo = cartRepo;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
public OrderDto createOrder(
        Authentication authentication,
        @RequestParam("restaurantBranchId") @Min(1) Long restaurantBranchId,
        @RequestBody @NotNull AddressDto addressDto
    ) {
        Customer customer = (Customer) authentication.getPrincipal();

        Order order = new Order();
        order.setCustomer(customer);
        order.getCustomer().setCart(cartRepo.findByCustomerId(customer.getId()));

        return orderConverter.convertToModel(orderService.createOrder(order, addressDto, restaurantBranchId), new OrderDto());
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public PageDto<OrderDto> getOrderList(
            Authentication authentication,

          @RequestParam(
                  value = "page",
                  defaultValue = "0",
                  required = false
          ) Integer page,

          @RequestParam(
                  value = "size",
                  defaultValue = "10",
                  required = false
          ) Integer size
    ) {

        Pageable pageable = PageRequest.of (page, size);
        Customer customer = (Customer) authentication.getPrincipal();
        return orderService.getOrdersByCustomer(customer, pageable);
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
    public OrderDto takeOrder(Authentication authentication, @PathVariable @Min(1) Long orderId) {
        Driver driver = (Driver) authentication.getPrincipal();
        return orderConverter.convertToModel(orderService.takeOrder(orderId, (Driver) driver), new OrderDto());
    }
}
