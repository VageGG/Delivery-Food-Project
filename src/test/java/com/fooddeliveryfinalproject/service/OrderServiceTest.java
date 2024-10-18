package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.OrderConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.model.PageDto;
import com.fooddeliveryfinalproject.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private RestaurantBranchRepo restaurantBranchRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private OrderItemRepo orderItemRepo;

    @Mock
    private MenuItemRepo menuItemRepo;

    @Mock
    private CartItemRepo cartItemRepo;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private DriverRepo driverRepo;

    @Mock
    private DeliveryRepo deliveryRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder() {
        // given
        Order order = new Order();
        order.setCustomer(new Customer());
        order.getCustomer().setId(1L);
        order.getCustomer().setCart(new Cart());
        order.getCustomer().getCart().setItems(new ArrayList<>());
        order.getCustomer().getCart().getItems().add(new CartItem());
        order.getCustomer().getCart().getItems().get(0).setMenuItemId(1L);
        order.setItems(new ArrayList<OrderItem>());
        order.getItems().add(new OrderItem());
        order.setDelivery(new Delivery());
        order.setStatus(Order.OrderStatus.PENDING);

        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(1L);

        AddressDto address = new AddressDto();
        address.setCity("Yerevan");
        address.setCountry("Armenia");
        address.setState("Yerevan");
        address.setStreet("Halabyan 2");
        address.setApartmentNumber("1");

        Delivery delivery = new Delivery();

        when(orderRepo.save(order)).thenReturn(order);

        when(cartRepo.findById(1L)).thenReturn(Optional.ofNullable(order.getCustomer().getCart()));

        when(customerRepo.findById(1L)).thenReturn(Optional.ofNullable(order.getCustomer()));

        when(restaurantBranchRepo.findById(1L)).thenReturn(Optional.of(new RestaurantBranch()));

        when(menuItemRepo.findById(1L)).thenReturn(Optional.of(menuItem));

        when(deliveryService.createDelivery(address, 1L, order)).thenReturn(delivery);

        when(orderItemRepo.save(new OrderItem(order, menuItem))).thenReturn(new OrderItem());

        // when
        Order savedOrder = orderService.createOrder(order, address, 1L);

        // then
        assertEquals(order.getCustomer(), savedOrder.getCustomer());
        assertEquals(order.getItems(), savedOrder.getItems());
    }

    @Test
    void createOrderShouldThrowNullPointerException() {
        Order order = null;
        assertThrows(NullPointerException.class, () -> orderService.createOrder(order, new AddressDto(), 1L));
    }

    @Test
    void createOrderShouldReturnNoItemsToOrder() {
        Order order = new Order();
        order.setItems(new ArrayList<>());
        assertThrows(RuntimeException.class, () -> orderService.createOrder(order, new AddressDto(), 1L));
    }

    @Test
    void getOrderById() {
        // given
        Order order = new Order();
        order.setOrderId(1);
        order.setCustomer(new Customer());
        order.setItems(new ArrayList<OrderItem>());
        order.setDelivery(new Delivery());
        order.setStatus(Order.OrderStatus.PENDING);

        Long orderId = 1L;

        // when
        when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));
        Order savedOrder = orderService.getOrderById(orderId);

        // then
        assertNotNull(savedOrder);
        assertEquals(savedOrder.getCustomer(), order.getCustomer());
    }

    @Test
    void getOrderByIdShouldThrowNullPointerException() {
        when(orderRepo.findById(1L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void calculateTotal() {
        // given
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();
        order.setOrderId(1L);
        order.setCustomer(new Customer());
        order.setDelivery(new Delivery());
        order.setStatus(Order.OrderStatus.PENDING);

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setPrice(2.3);

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setPrice(5.0);

        orderItems.add(new OrderItem(order, menuItem1));
        orderItems.add(new OrderItem(order, menuItem2));

        order.setItems(orderItems);

        when(orderRepo.findById(order.getOrderId())).thenReturn(Optional.of(order));

        // when
        Double total = orderService.calculateTotal(order.getOrderId());

        // then
        assertEquals(
                orderItems.get(0).getMenuItem().getPrice() +
                        orderItems.get(1).getMenuItem().getPrice(),
                total
        );
    }

    @Test
    void calculateTotalShouldReturnCartIsEmpty() {
        Order order = new Order();
        order.setItems(new ArrayList<>());

        when(orderRepo.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class, () -> orderService.calculateTotal(1L));
    }

    @Test
    void deleteOrder() {
        // given
        Order order = new Order();
        order.setOrderId(1L);
        order.setCustomer(new Customer());
        order.setDelivery(new Delivery());
        order.setStatus(Order.OrderStatus.PENDING);

        // when
        when(orderRepo.findById(order.getOrderId())).thenReturn(Optional.of(order));
        String message = orderService.deleteOrder(order.getOrderId());

        // then
        assertEquals(message, "deleted");
    }

    @Test
    void deleteOrderShouldThrowNullPointerException() {
        when(orderRepo.findById(1L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void getOrdersPageByCustomer() {
        // given
        Long customerId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        List<Order> orders = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(customerId);

        Page<Order> page = new PageImpl<>(orders);

        Order order1 = new Order();
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setCustomer(customer);

        orders.add(order1);
        orders.add(order2);

        List<OrderDto> orderDtos = new ArrayList<>();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerId);
        OrderDto orderDto1 = new OrderDto();

        OrderDto orderDto2 = new OrderDto();
        orderDtos.add(orderDto1);
        orderDtos.add(orderDto2);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        when(orderRepo.findByCustomer(customer, pageable)).thenReturn(page);

        when(orderRepo.findByCustomerId(1L)).thenReturn(orders);

        when(orderConverter.convertToModel(
                    order1,
                    new OrderDto()
                )
        ).thenReturn(orderDto1);

        when(orderConverter.convertToModel(
                    order2,
                    new OrderDto()
                )
        ).thenReturn(orderDto2);

        // when
        PageDto<OrderDto> response = orderService.getOrdersByCustomer(customer, pageable);

        // then
        assertEquals(orderDtos.size(), response.getContent().size());
        assertEquals(orderDtos.get(0), response.getContent().get(0));
        assertEquals(orderDtos.get(1), response.getContent().get(1));
    }

    @Test
    void getPendingOrdersList() {
        // given
        List<Order> orders = new ArrayList<>();

        Order order1 = new Order();
        order1.setStatus(Order.OrderStatus.PENDING);
        order1.setDelivery(new Delivery());
        order1.getDelivery().setDriver(new Driver());
        order1.getDelivery().getDriver().setId(1L);

        Order order2 = new Order();
        order2.setStatus(Order.OrderStatus.PENDING);
        order2.setDelivery(new Delivery());
        order2.getDelivery().setDriver(new Driver());
        order2.getDelivery().getDriver().setId(1L);

        orders.add(order1);
        orders.add(order2);

        List<OrderDto> orderDtos = new ArrayList<>();

        OrderDto orderDto1 = new OrderDto();
        orderDto1.setStatus(Order.OrderStatus.PENDING);

        OrderDto orderDto2 = new OrderDto();
        orderDto2.setStatus(Order.OrderStatus.PENDING);

        orderDtos.add(orderDto1);
        orderDtos.add(orderDto2);

        when(orderRepo.findByStatus(Order.OrderStatus.PENDING)).thenReturn(orders);
        when(orderConverter.convertToModel(
                orders.get(0), new OrderDto()
            )
        ).thenReturn(orderDtos.get(0));
        when(orderConverter.convertToModel(
                orders.get(1),
                new OrderDto()
            )
        ).thenReturn(orderDtos.get(1));

        // when
        List<OrderDto> responseDtos = orderService.getPendingOrdersList();

        // then
        assertEquals(orders.size(), responseDtos.size());
        assertEquals(orderDtos.get(0), responseDtos.get(0));
        assertEquals(orderDtos.get(1), responseDtos.get(1));
    }

    @Test
    void takeOrder() {
        // given
        Order order = new Order();
        order.setOrderId(1L);
        order.setStatus(Order.OrderStatus.PICKED_UP);
        order.setDelivery(new Delivery());

        Driver driver = new Driver();
        driver.setId(1L);

        Delivery delivery = new Delivery();
        delivery.setOrder(order);

        when(orderRepo.findById(order.getOrderId())).thenReturn(Optional.of(order));
        when(driverRepo.findById(1L)).thenReturn(Optional.of(driver));
        when(driverRepo.findByDeliveries(delivery)).thenReturn(delivery.getDriver());
        when(deliveryRepo.findByOrder(order)).thenReturn(delivery);
        when(orderRepo.save(order)).thenReturn(order);

        // when
        Order response = orderService.takeOrder(order.getOrderId(), driver);

        // then
        assertEquals(order.getOrderId(), response.getOrderId());
        assertEquals(order.getStatus(), response.getStatus());
    }
}