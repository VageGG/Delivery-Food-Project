package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.OrderItem;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.repository.DeliveryRepo;
import com.fooddeliveryfinalproject.repository.OrderRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private DeliveryRepo deliveryRepo;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private OrderConverter converter;

    @Transactional
    public Order createOrder(Order order) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (order == null) {
            throw new NullPointerException("order can't be null");
        }

        if (order.getItems().isEmpty()) {
            throw new RuntimeException("no items to order");
        }

        order.getDelivery().setOrderTime(dateTime);
        order.setStatus(Order.OrderStatus.PENDING);

        return this.orderRepo.save(order);
    }

    public Order getOrderById(long id) {
        Order order = this.orderRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("order not found"));
        
        return order;
    }

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

    public Page<OrderDto> getOrdersByCustomer(Long customerId, Pageable pageable) {
        // TODO: implement logic to fetch orders by customer and map to OrderDto
        // Placeholder for actual implementation

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
        return page;
    }

    public List<OrderDto> getPendingOrdersList() {
        List<OrderDto> orderDtos = orderRepo.findByStatus(Order.OrderStatus.PENDING).stream()
                .map(order -> converter.convertToModel(order, new OrderDto()))
                .collect(Collectors.toList());

        return orderDtos;
    }

    @Transactional
    public Order takeOrder(Long orderId) {
        Order order = getOrderById(orderId);

        order.setStatus(Order.OrderStatus.PICKED_UP);
        return orderRepo.save(order);
    }
}
