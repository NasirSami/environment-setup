package com.example.payment;

import java.time.Instant;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 * Mock PayPal processor. Declared with stereotype annotation for component scanning.
 */
@Service("paypalPaymentService")
public class PaypalPaymentService implements PaymentService {

    private final String instanceId = "PAYPAL-" + UUID.randomUUID();

    @Override
    public String getName() {
        return "PayPal";
    }

    @Override
    public PaymentReceipt process(PaymentRequest request) {
        String confirmation = "PP-" + Instant.now().toEpochMilli();
        String message = "Captured payment of " + request.getAmount() + " " + request.getCurrency()
                + " using PayPal wallet.";
        return new PaymentReceipt(confirmation, message, instanceId);
    }

    @PostConstruct
    public void init() {
        System.out.println("[PaypalPaymentService] Ready with instance " + instanceId);
    }
}
