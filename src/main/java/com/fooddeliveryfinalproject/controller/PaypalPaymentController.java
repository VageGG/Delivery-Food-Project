package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.service.CartService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.fooddeliveryfinalproject.service.PaypalPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class PaypalPaymentController {
    @Autowired
    private PaypalPaymentService paypalService;
    @Autowired
    private CartService cartService;

    @PostMapping("/paypal/payment/create")
    public RedirectView createPayment(@RequestParam long customerId) throws PayPalRESTException {
        double total = this.cartService.calculateTotal(customerId);

        String cancelUrl = "http://localhost:8081/paypal/payment/cancel";
        String successUrl = "http://localhost:8081/paypal/payment/success";

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

    @GetMapping("/paypal/payment/success")
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


}
