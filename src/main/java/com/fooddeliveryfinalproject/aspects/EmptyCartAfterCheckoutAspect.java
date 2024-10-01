package com.fooddeliveryfinalproject.aspects;

import com.fooddeliveryfinalproject.controller.PaypalPaymentController;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.OrderRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy
@Component
public class EmptyCartAfterCheckoutAspect {
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PaypalPaymentController paypalPaymentController;

    @AfterReturning("com.fooddeliveryfinalproject.controller.executePayment()")
    public void emptyCart(JoinPoint joinPoint) {
        System.out.println("================ emptying cart =======================");
        long orderId = paypalPaymentController.getId();

        Cart cart = orderRepo.findById(orderId).get().getCustomer().getCart();
        cart.getItems().clear();

//        for (CartItem cartItem : cart.getItems()) {
//            cart.getItems().remove(cartItem);
//        }

        cartRepo.save(cart);

        System.out.println("===isCartEmpty==== " + cart.getItems().isEmpty());
    }
}
