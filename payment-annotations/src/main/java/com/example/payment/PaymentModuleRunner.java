package com.example.payment;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Entry point that bootstraps Spring using annotation-based configuration to showcase bean scopes and injection.
 */
public final class PaymentModuleRunner {

    private PaymentModuleRunner() {
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(PaymentModuleConfiguration.class)) {

            PaymentRequest request = context.getBean(PaymentRequest.class);

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
