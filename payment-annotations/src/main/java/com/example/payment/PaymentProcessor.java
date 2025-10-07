package com.example.payment;

import java.util.Objects;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Business component representing the checkout workflow. Spring injects the desired payment
 * implementation via constructor-based dependency injection.
 */
public class PaymentProcessor {

    private final PaymentService paymentService;

    @Autowired
    public PaymentProcessor(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentReceipt handle(PaymentRequest request) {
        Objects.requireNonNull(paymentService, "paymentService must be injected by Spring");
        return paymentService.process(request);
    }

    @PostConstruct
    public void init() {
        System.out.println("[PaymentProcessor] Initialized with service " + paymentService.getName());
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("[PaymentProcessor] Shutting down processor for service " + paymentService.getName());
    }

    @Override
    public String toString() {
        return "PaymentProcessor{" +
                "paymentService=" + paymentService.getName() +
                '}';
    }
}
