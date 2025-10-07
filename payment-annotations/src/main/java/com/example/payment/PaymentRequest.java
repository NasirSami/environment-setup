package com.example.payment;

import java.math.BigDecimal;

/**
 * Simple value object representing a payment request coming from the checkout flow.
 */
public final class PaymentRequest {

    private final String orderId;
    private final BigDecimal amount;
    private final String currency;

    public PaymentRequest(String orderId, BigDecimal amount, String currency) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
