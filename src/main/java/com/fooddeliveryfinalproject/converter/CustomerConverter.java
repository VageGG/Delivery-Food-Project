package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerConverter implements Converter<Customer, CustomerDto> {

    private final OrderConverter orderConverter;
    private final CustomerAddressConvertor customerAddressConvertor;
    private final PaymentMethodConverter paymentMethodConverter;

    @Autowired
    @Lazy
    private CartConverter cartConverter;

    public CustomerConverter(OrderConverter orderConverter,
                             CustomerAddressConvertor addressConverter,
                             PaymentMethodConverter paymentMethodConverter) {
        this.orderConverter = orderConverter;
        this.customerAddressConvertor = addressConverter;
        this.paymentMethodConverter = paymentMethodConverter;
    }

    @Override
    public Customer convertToEntity(CustomerDto model, Customer entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());

        if (model.getOrdersDto() != null) {
            List<Order> orders = orderConverter.convertToEntityList(model.getOrdersDto(), Order::new);
            entity.setOrders(orders);
        }

        if (model.getAddressesDto() != null) {
            List<CustomerAddress> addresses = customerAddressConvertor.convertToEntityList(model.getAddressesDto(), CustomerAddress::new);
            entity.setAddresses(addresses);
        }

        if (model.getCartDto()!= null) {
            entity.setCart(cartConverter.convertToEntity(model.getCartDto(), new Cart()));
        }

        if (model.getPaymentMethodsDto() != null) {
            List<PaymentMethod> paymentMethods = paymentMethodConverter.convertToEntityList(model.getPaymentMethodsDto(), PaymentMethod::new);
            entity.setPaymentMethods(paymentMethods);
        }

        return entity;
    }

    @Override
    public CustomerDto convertToModel(Customer entity, CustomerDto model) {
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());

        if (entity.getOrders() != null) {
            List<OrderDto> orderDtos = orderConverter.convertToModelList(entity.getOrders(), OrderDto::new);
            model.setOrdersDto(orderDtos);
        }

        if (entity.getAddresses() != null) {
            List<CustomerAddressDto> addressDtos = customerAddressConvertor.convertToModelList(entity.getAddresses(), CustomerAddressDto::new);
            model.setAddressesDto(addressDtos);
        }

        if (entity.getCart()!= null) {
            model.setCartDto(cartConverter.convertToModel(entity.getCart(), new CartDto()));
        }

        if (entity.getPaymentMethods() != null) {
            List<PaymentMethodDto> paymentMethodDtos = paymentMethodConverter.convertToModelList(entity.getPaymentMethods(), PaymentMethodDto::new);
            model.setPaymentMethodsDto(paymentMethodDtos);
        }

        return model;
    }
}
