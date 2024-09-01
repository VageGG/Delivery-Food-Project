package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.Status;
import com.fooddeliveryfinalproject.converter.OrderCartConverter;
import com.fooddeliveryfinalproject.entity.OrderCart;
import com.fooddeliveryfinalproject.model.IdDto;
import com.fooddeliveryfinalproject.model.OrderCartDto;
import com.fooddeliveryfinalproject.service.OrderCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderCartController {
    @Autowired
    private OrderCartService service;
    private OrderCartConverter converter = new OrderCartConverter();

    @PostMapping(value = "/order/cart/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public IdDto createOrderCart(@RequestBody OrderCartDto orderCartDto) {
        return new IdDto(this.service.createOrderCart(this.converter.convertToEntity(orderCartDto, new OrderCart())).getCartId());
    }

    @GetMapping(value = "/order/cart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderCartDto getOrderCartById(@PathVariable long id) {
        return this.converter.convertToModel(this.service.getOrderCartById(id), new OrderCartDto());
    }

    @PutMapping("/order/cart/update")
    public String updateOrderCart(@RequestBody OrderCartDto orderCartDto) {
        this.service.updateOrderCart(this.converter.convertToEntity(orderCartDto, new OrderCart()));
        return "status: " + Status.UPDATED;
    }

    @DeleteMapping("/order/cart/delete/{id}")
    public String deleteOrderCart(@PathVariable long id) {
        this.service.deleteOrderCart(id);
        return "status: " + Status.DELETED;
    }
}
