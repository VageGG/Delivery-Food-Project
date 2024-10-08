package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.model.PageDto;
import com.fooddeliveryfinalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    private final DeliveryRepo deliveryRepo;

    private final DateTimeService dateTimeService;

    private final OrderConverter converter;

    private final CustomerRepo customerRepo;

    private final MenuItemRepo menuItemRepo;

    private final DeliveryService deliveryService;

    private final OrderItemRepo orderItemRepo;

    private final DriverRepo driverRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo,
                        DeliveryRepo deliveryRepo,
                        DateTimeService dateTimeService,
                        OrderConverter converter,
                        CustomerRepo customerRepo,
                        MenuItemRepo menuItemRepo,
                        DeliveryService deliveryService,
                        OrderItemRepo orderItemRepo,
                        DriverRepo driverRepo) {
        this.orderRepo = orderRepo;
        this.deliveryRepo = deliveryRepo;
        this.dateTimeService = dateTimeService;
        this.converter = converter;
        this.customerRepo = customerRepo;
        this.menuItemRepo = menuItemRepo;
        this.deliveryService = deliveryService;
        this.orderItemRepo = orderItemRepo;
        this.driverRepo = driverRepo;
    }

    @Transactional
    public Order createOrder(Order order, AddressDto addressDto, Long restaurantBranchId) {
        Order savedOrder = orderRepo.save(order);

        if (order.getCustomer() == null) {
            orderRepo.delete(savedOrder);
            throw new NullPointerException("customer not found");
        }

        if (order.getCustomer().getCart() == null) {
            orderRepo.delete(savedOrder);
            throw new NullPointerException("cart not found");
        }

        List<CartItem> cartItems = order.getCustomer().getCart().getItems();

        if (cartItems.isEmpty()) {
            orderRepo.delete(savedOrder);
            throw new NullPointerException("no items to order");
        }

        savedOrder.setStatus(Order.OrderStatus.PENDING);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> orderItemRepo.save(new OrderItem(savedOrder, menuItemRepo.findById(cartItem.getMenuItemId()).get())))
                .toList();

        savedOrder.setDelivery(deliveryService.createDelivery(addressDto, restaurantBranchId, savedOrder));

        return this.orderRepo.save(savedOrder);

    }

    @Transactional(readOnly = true)
    public Order getOrderById(long id) {
        Order order = this.orderRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("order not found"));
        
        return order;
    }

    @Transactional
    public Double calculateTotal(long orderId) {
        List<OrderItem> items = getOrderById(orderId).getItems();

        if (items.isEmpty()) {
            throw new RuntimeException("cart is empty");
        }

        double total = 0;
        for (OrderItem orderItem: items) {
            total+= orderItem.getMenuItem().getPrice();
        }

        return total;
    }

    @Transactional
    public String deleteOrder(long id) {
        Order order = getOrderById(id);
        this.orderRepo.delete(order);
        return "deleted";
    }

    @Transactional
    public PageDto<OrderDto> getOrdersByCustomer(Long customerId, Pageable pageable) {
        List<OrderDto> orders = orderRepo.findByCustomerId(customerId).stream()
                .map(order -> converter.convertToModel(order, new OrderDto()))
                .collect(Collectors.toList());

        Page<OrderDto> page =
                new PageImpl<>(orders.subList (
                        pageable.getPageNumber(),
                        pageable.getPageSize()
                ),
                pageable,
                orders.size()
        );

        return new PageDto<>(page);
    }

    @Transactional
    public List<OrderDto> getPendingOrdersList() {
        List<OrderDto> orderDtos = orderRepo.findByStatus(Order.OrderStatus.PENDING).stream()
                .map(order -> converter.convertToModel(order, new OrderDto()))
                .collect(Collectors.toList());

        return orderDtos;
    }

    @Transactional
    public Order takeOrder(Long orderId, Long driverId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new NullPointerException("order not found"));

        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new NullPointerException("driver not found"));

        if (driverRepo.findByDeliveries(deliveryRepo.findByOrder(order)) != null) {
            throw new RuntimeException("this order has already been taken by another driver");
        }

        order.getDelivery().setDriver(driver);

        return orderRepo.save(order);
    }
}
