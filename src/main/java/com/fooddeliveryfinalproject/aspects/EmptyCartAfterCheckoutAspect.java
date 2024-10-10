package com.fooddeliveryfinalproject.aspects;

import com.fooddeliveryfinalproject.controller.PaypalPaymentController;
import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.entity.Order;
import com.fooddeliveryfinalproject.entity.OrderItem;
import com.fooddeliveryfinalproject.repository.CartItemRepo;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.OrderRepo;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@EnableAspectJAutoProxy
@Component
public class EmptyCartAfterCheckoutAspect {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private PaypalPaymentController paypalPaymentController;


    @AfterReturning(
            pointcut = "execution(* com.fooddeliveryfinalproject.controller.PaypalPaymentController.executePayment(..))",
            returning = "success"
    )
    public void emptyCart(Object success) throws Throwable {
        List<OrderItem> orderItems = orderRepo.findById(paypalPaymentController.getId()).get().getItems();

        List<CartItem> cartItems = orderItems.stream()
                .map(orderItem -> cartItemRepo.findByCartId(orderItem.getOrder().getCustomer().getCart().getCartId()))
                .toList();

        for (CartItem cartItem: cartItems) {
            cartItemRepo.delete(cartItem);
        }

        Order order = orderRepo.findById(paypalPaymentController.getId()).get();

        order.setStatus(Order.OrderStatus.PAID);

        orderRepo.save(order);
    }
}
