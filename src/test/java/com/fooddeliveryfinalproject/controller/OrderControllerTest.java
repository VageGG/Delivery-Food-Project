package com.fooddeliveryfinalproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.BlacklistService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(OrderController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private BlacklistService blacklistService;

    @MockBean
    private OrderConverter orderConverter;

    @MockBean
    private JWTUtilService jwtUtilService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createOrder() throws Exception {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem());

        Order order = new Order();
        order.setOrderId(1L);

        Customer customer = new Customer();
        customer.setId(1L);
        order.setCustomer(customer);

        Delivery delivery = new Delivery();
        delivery.setDeliveryId(1L);
        order.setDelivery(delivery);

        order.setStatus(Order.OrderStatus.PENDING);
        order.setItems(items);

        List<OrderItemDto> orderItemDtos = new ArrayList<>();

        orderItemDtos.add(new OrderItemDto());

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1L);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);

        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setId(1L);

        orderDto.setStatus(Order.OrderStatus.PENDING);

        when(orderConverter.convertToModel(order, new OrderDto())).thenReturn(orderDto);
        when(orderConverter.convertToEntity(orderDto, new Order())).thenReturn(order);
        BDDMockito.given(orderService.createOrder(order))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void getOrderList() throws Exception {
        List<OrderDto> orderDtos = new ArrayList<>();
        OrderDto orderDto1 = new OrderDto();
        OrderDto orderDto2 = new OrderDto();
        OrderDto orderDto3 = new OrderDto();

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);

        orderDtos.add(orderDto1);
        orderDtos.add(orderDto2);
        orderDtos.add(orderDto3);

        Pageable pageable = PageRequest.of (
                0,
                2
        );

        Page<OrderDto> page = new PageImpl<>(orderDtos);

        when(orderService.getOrdersByCustomer(1L, pageable)).thenReturn(page);

        ResultActions response = mockMvc.perform(get("/order/list?customerId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(page))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getOrderById() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1L);

        when(orderService.getOrderById(1L))
                .thenReturn(order);
        when(orderConverter.convertToModel(order, new OrderDto()))
                .thenReturn(orderDto);

        ResultActions response = mockMvc.perform(get("/order/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPendingOrdersList() throws Exception {
        List<Order> orders = new ArrayList<>();

        Order order1 = new Order();
        order1.setOrderId(1L);
        order1.setDelivery(new Delivery());
        order1.getDelivery().setDriver(new Driver());
        order1.getDelivery().getDriver().setId(1L);
        order1.setStatus(Order.OrderStatus.PENDING);

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setDelivery(new Delivery());
        order2.getDelivery().setDriver(new Driver());
        order2.getDelivery().getDriver().setId(1L);
        order2.setStatus(Order.OrderStatus.PENDING);

        orders.add(order1);
        orders.add(order2);

        List<OrderDto> orderDtos = new ArrayList<>();

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setOrderId(1L);
        orderDto1.setStatus(Order.OrderStatus.PENDING);

        OrderDto orderDto2 = new OrderDto();
        orderDto2.setOrderId(2L);
        orderDto2.setStatus(Order.OrderStatus.PENDING);

        orderDtos.add(orderDto1);
        orderDtos.add(orderDto2);

        when(orderService.getPendingOrdersList()).thenReturn(orderDtos);

        ResultActions response = mockMvc.perform(get("/order/pending/list")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderDtos)
                )
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void takeOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(1L);
        order.setStatus(Order.OrderStatus.PICKED_UP);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(1L);
        orderDto.setStatus(Order.OrderStatus.PICKED_UP);

        when(orderService.takeOrder(1L)).thenReturn(order);
        when(orderConverter.convertToModel(order, new OrderDto())).thenReturn(orderDto);

        ResultActions response = mockMvc.perform(post("/order/1/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}