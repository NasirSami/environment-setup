package com.example.payment;

import java.time.Instant;
import java.util.UUID;

/**
 * Mock PayPal processor. Declared with prototype scope to highlight bean instance differences.
 */
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
}
