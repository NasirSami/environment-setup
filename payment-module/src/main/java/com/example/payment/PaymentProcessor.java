package com.example.payment;

import java.util.Objects;

/**
 * Business component representing the checkout workflow. Spring injects the desired payment
 * implementation via setter-based dependency injection.
 */
public class PaymentProcessor {

    private PaymentService paymentService;

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentReceipt handle(PaymentRequest request) {
        Objects.requireNonNull(paymentService, "paymentService must be injected by Spring");
        return paymentService.process(request);
    }

    @Override
    public String toString() {
        return "PaymentProcessor{" +
                "paymentService=" + (paymentService != null ? paymentService.getName() : "not configured") +
                '}';
    }
}
