package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Delivery;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.DeliveryDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter implements Converter<Order, OrderDto> {

    private final CustomerConverter customerConverter;

    private final MenuItemConverter menuItemConverter;

    private final DeliveryConverter deliveryConverter;

    public OrderConverter(CustomerConverter customerConverter,
                          MenuItemConverter menuItemConverter,
                          DeliveryConverter deliveryConverter) {
        this.customerConverter = customerConverter;
        this.menuItemConverter = menuItemConverter;
        this.deliveryConverter = deliveryConverter;
    }

    @Override
    public Order convertToEntity(OrderDto model, Order entity) {
        entity.setOrderId(model.getOrderId());

        if (model.getCustomerDto() != null) {
            entity.setCustomer(customerConverter.convertToEntity(model.getCustomerDto(), new Customer()));
        }

        if (model.getItemsDto() != null) {
            List<MenuItem> menuItems = new ArrayList<>();
            for (MenuItemDto itemDto : model.getItemsDto()) {
                menuItems.add(menuItemConverter.convertToEntity(itemDto, new MenuItem()));
            }
            entity.setItems(menuItems);
        }

        if (model.getDeliveryDto() != null) {
            entity.setDelivery(deliveryConverter.convertToEntity(model.getDeliveryDto(), new Delivery()));
        }

        entity.setStatus(model.getStatus());
        return entity;
    }

    @Override
    public OrderDto convertToModel(Order entity, OrderDto model) {
        model.setOrderId(entity.getOrderId());

        if (entity.getCustomer() != null) {
            model.setCustomerDto(customerConverter.convertToModel(entity.getCustomer(), new CustomerDto()));
        }

        if (entity.getItems() != null) {
            List<MenuItemDto> menuItemsDto = new ArrayList<>();
            for (MenuItem menuItem : entity.getItems()) {
                menuItemsDto.add(menuItemConverter.convertToModel(menuItem, new MenuItemDto()));
            }
            model.setItemsDto(menuItemsDto);
        }

        if (entity.getDelivery() != null) {
            model.setDeliveryDto(deliveryConverter.convertToModel(entity.getDelivery(), new DeliveryDto()));
        }

        model.setStatus(entity.getStatus());
        return model;
    }
}
