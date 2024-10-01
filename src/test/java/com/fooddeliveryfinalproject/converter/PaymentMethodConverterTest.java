package com.fooddeliveryfinalproject.converter;

import com.fooddeliveryfinalproject.entity.Payment;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentMethodConverterTest {

    private PaymentMethodConverter converter;

    @BeforeEach
    void setUp() {
        converter = new PaymentMethodConverter();
    }

    @Test
    void convertToEntity_ShouldConvertDtoToEntity() {
        PaymentMethodDto dto = new PaymentMethodDto();
        dto.setId(1L);
        dto.setPaymentMethodType(Payment.PaymentMethodType.CARD);
        dto.setDetails("1234-5678-9012-3456");

        PaymentMethod entity = new PaymentMethod();
        converter.convertToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertEquals(Payment.PaymentMethodType.CARD, entity.getPaymentMethodType());
        assertEquals("1234-5678-9012-3456", entity.getDetails());
    }

    @Test
    void convertToModel_ShouldConvertEntityToDto() {
        PaymentMethod entity = new PaymentMethod();
        entity.setId(1L);
        entity.setPaymentMethodType(Payment.PaymentMethodType.CARD);
        entity.setDetails("1234-5678-9012-3456");

        PaymentMethodDto dto = new PaymentMethodDto();
        converter.convertToModel(entity, dto);

        assertEquals(1L, dto.getId());
        assertEquals(Payment.PaymentMethodType.CARD, dto.getPaymentMethodType());
        assertEquals("1234-5678-9012-3456", dto.getDetails());
    }
}
