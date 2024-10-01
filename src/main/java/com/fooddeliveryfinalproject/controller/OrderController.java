package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    private final OrderConverter orderConverter;

    @Autowired
    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.createOrder(
                orderConverter.convertToEntity(
                        orderDto,
                        new Order()
                )
        );
        return orderConverter.convertToModel(order, new OrderDto());
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public Page<OrderDto> getOrderList(@RequestParam Long customerId) { // ask how to get customerId
        Pageable pageable = PageRequest.of (
                0,
                2
        );
        return orderService.getOrdersByCustomer(customerId, pageable);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderDto getOrderById(@PathVariable Long orderId) {
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
    public OrderDto takeOrder(@PathVariable Long orderId) {
        return orderConverter.convertToModel(orderService.takeOrder(orderId), new OrderDto());
    }
}
