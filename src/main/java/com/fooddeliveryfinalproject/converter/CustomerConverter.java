package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.OrderDto;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerConverter implements Converter<Customer, CustomerDto> {

    private final OrderConverter orderConverter;
    private final AddressConverter addressConverter;
    private final PaymentMethodConverter paymentMethodConverter;

    public CustomerConverter(OrderConverter orderConverter,
                             AddressConverter addressConverter,
                             PaymentMethodConverter paymentMethodConverter) {
        this.orderConverter = orderConverter;
        this.addressConverter = addressConverter;
        this.paymentMethodConverter = paymentMethodConverter;
    }
    @Override
    public Customer convertToEntity(CustomerDto model, Customer entity) {
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setEmail(model.getEmail());
        entity.setPhoneNumber(model.getPhoneNumber());

        if (model.getOrdersDto() != null) {
            List<Order> orders = new ArrayList<>();
            for (OrderDto orderDto : model.getOrdersDto()) {
                orders.add(orderConverter.convertToEntity(orderDto, new Order()));
            }
            entity.setOrders(orders);
        }

        if (model.getAddressesDto() != null) {
            List<Address> addresses = new ArrayList<>();
            for (AddressDto addressDto : model.getAddressesDto()) {
                addresses.add(addressConverter.convertToEntity(addressDto, new Address()));
            }
            entity.setAddresses(addresses);
        }

        if (model.getPaymentMethodsDto() != null) {
            List<PaymentMethod> paymentMethods = new ArrayList<>();
            for (PaymentMethodDto paymentMethodDto : model.getPaymentMethodsDto()) {
                paymentMethods.add(paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod()));
            }
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
            List<OrderDto> orderDtos = new ArrayList<>();
            for (Order order : entity.getOrders()) {
                orderDtos.add(orderConverter.convertToModel(order, new OrderDto()));
            }
            model.setOrdersDto(orderDtos);
        }

        if (entity.getAddresses() != null) {
            List<AddressDto> addressDtos = new ArrayList<>();
            for (Address address : entity.getAddresses()) {
                addressDtos.add(addressConverter.convertToModel(address, new AddressDto()));
            }
            model.setAddressesDto(addressDtos);
        }

        if (entity.getPaymentMethods() != null) {
            List<PaymentMethodDto> paymentMethodDtos = new ArrayList<>();
            for (PaymentMethod paymentMethod : entity.getPaymentMethods()) {
                paymentMethodDtos.add(paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()));
            }
            model.setPaymentMethodsDto(paymentMethodDtos);
        }
        return model;
    }
}
