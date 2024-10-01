package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.service.OrderService;
import com.fooddeliveryfinalproject.service.PaypalPaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class PaypalPaymentController {

    private final PaypalPaymentService paypalService;

    private final OrderService orderService;

    private long id;



    @PostMapping("/order/{orderId}/pay")
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public RedirectView createPayment(@PathVariable long orderId) throws PayPalRESTException {
        double total = this.orderService.calculateTotal(orderId);

        id = orderId;

        String cancelUrl = "http://localhost:8081/paypal/payment/cancel";
        String successUrl = "http://localhost:8081/paypal/payment/execute";

        Payment payment = paypalService.createPayment (
                total,
                "USD",
                "Paypal",
                "sale",
                "paypal payment",
                cancelUrl,
                successUrl
        );

        for(Links link: payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                System.out.println("link = " + link.getHref());
                return new RedirectView(link.getHref());
            }
        }

        return new RedirectView("/payment/error");
    }

    @GetMapping("/paypal/payment/execute")
    public String executePayment (
        @RequestParam("paymentId") String paymentId,
        @RequestParam("PayerID") String payerId
    )
        throws PayPalRESTException {

        Payment payment = paypalService.executePayment(payerId, paymentId);

        if (payment.getState().equals("approved")) {
            return "success";
        }

        throw new RuntimeException("an error occurred");
    }

    @GetMapping("/paypal/payment/error")
    public String paymentError() {
        return "error";
    }

    @GetMapping("/paypal/payment/cancel")
    public String cancelPayment() {
        return "cancel";
    }

    public long getId() {
        return id;
    }
}
