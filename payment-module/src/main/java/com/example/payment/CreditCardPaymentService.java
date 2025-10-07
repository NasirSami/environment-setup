package com.example.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

/**
 * Mock credit card processor used to demonstrate Spring injecting a concrete implementation.
 */
public class CreditCardPaymentService implements PaymentService {

    private final String instanceId = UUID.randomUUID().toString();

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public PaymentReceipt process(PaymentRequest request) {
        BigDecimal fee = request.getAmount().multiply(new BigDecimal("0.025")).setScale(2, RoundingMode.HALF_UP);
        String confirmation = "CC-" + Instant.now().toEpochMilli();
        String message = "Charged " + request.getAmount() + " " + request.getCurrency()
                + " (fee " + fee + ") via credit card gateway.";
        return new PaymentReceipt(confirmation, message, instanceId);
    }
}
