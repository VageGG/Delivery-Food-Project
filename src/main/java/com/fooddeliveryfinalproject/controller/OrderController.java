package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.Status;
import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.IdDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderService service;
    private OrderConverter converter = new OrderConverter();

    @PostMapping(value = "/order/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public IdDto createOrder(@RequestBody OrderDto orderDto) {
        return new IdDto(this.service.createOrder(this.converter.convertToEntity(orderDto, new Order())).getOrderId());
    }

    @GetMapping(value = "/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto getOrderById(@PathVariable long id) {
        return this.converter.convertToModel(this.service.getOrderById(id), new OrderDto());
    }

    @PutMapping("/order/update") //ask if it's possible
    public String updateOrder(@RequestBody OrderDto orderDto) {
        this.service.updateOrder(this.converter.convertToEntity(orderDto, new Order()));
        return "status: " + Status.UPDATED;
    }

    @DeleteMapping("/order/delete/{id}") //ask how to do it
    public String deleteOrder(@PathVariable long id) {
        this.service.deleteOrder(id);
        return "status: " + Status.DELETED;
    }
}
