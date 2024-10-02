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


    @AfterReturning(pointcut = "execution(* com.fooddeliveryfinalproject.controller.PaypalPaymentController.executePayment(..))")
    public void emptyCart(JoinPoint joinPoint) {
        System.out.println("================ emptying cart =======================");

        try {
            // Получаем orderId из параметров метода executePayment
            Object[] args = joinPoint.getArgs();
            long orderId = (Long) args[0];  // Если ваш метод принимает orderId как параметр

            // Находим заказ и корзину покупателя
            Cart cart = orderRepo.findById(orderId).get().getCustomer().getCart();

            // Очищаем корзину
            cart.getItems().clear();
            cartRepo.save(cart);

            System.out.println("===isCartEmpty==== " + cart.getItems().isEmpty());
        } catch (Exception e) {
            System.out.println("Error during cart emptying: " + e.getMessage());
            throw e;
        }
    }
}
