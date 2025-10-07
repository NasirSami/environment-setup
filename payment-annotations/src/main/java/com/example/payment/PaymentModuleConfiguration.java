package com.example.payment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Annotation-driven configuration equivalent to the XML wiring in the base payment module.
 */
@Configuration
@ComponentScan(basePackages = "com.example.payment")
public class PaymentModuleConfiguration {

    @Bean
    public PaymentRequest samplePaymentRequest() {
        return new PaymentRequest("ORD-1001", java.math.BigDecimal.valueOf(149.99), "USD");
    }

    @Bean
    public PaymentProcessor creditCardProcessor(@Qualifier("creditCardPaymentService") PaymentService paymentService) {
        return new PaymentProcessor(paymentService);
    }

    @Bean
    public PaymentProcessor paypalProcessor(@Qualifier("paypalPaymentService") PaymentService paymentService) {
        return new PaymentProcessor(paymentService);
    }
}
