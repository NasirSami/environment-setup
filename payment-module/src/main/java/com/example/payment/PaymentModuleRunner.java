package com.example.payment;

import java.math.BigDecimal;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point that bootstraps Spring using XML configuration to showcase bean scopes and injection.
 */
public final class PaymentModuleRunner {

    private PaymentModuleRunner() {
    }

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {

            PaymentRequest request = new PaymentRequest("ORD-1001", new BigDecimal("149.99"), "USD");

            PaymentProcessor creditCardProcessor = context.getBean("creditCardProcessor", PaymentProcessor.class);
            PaymentReceipt ccReceipt = creditCardProcessor.handle(request);
            System.out.println("Credit card receipt: " + ccReceipt);

            PaymentProcessor firstPaypalProcessor = context.getBean("paypalProcessor", PaymentProcessor.class);
            PaymentReceipt firstPaypalReceipt = firstPaypalProcessor.handle(request);
            System.out.println("PayPal receipt (processor 1): " + firstPaypalReceipt);

            PaymentProcessor secondPaypalProcessor = context.getBean("paypalProcessor", PaymentProcessor.class);
            PaymentReceipt secondPaypalReceipt = secondPaypalProcessor.handle(request);
            System.out.println("PayPal receipt (processor 2): " + secondPaypalReceipt);

            System.out.println("First PayPal processor instance: " + firstPaypalProcessor);
            System.out.println("Second PayPal processor instance: " + secondPaypalProcessor);
        }
    }
}
