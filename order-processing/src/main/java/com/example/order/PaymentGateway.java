package com.example.order;

import java.math.BigDecimal;

/**
 * Gateway abstraction for charging customer payments.
 */
public interface PaymentGateway {

    PaymentReceipt charge(String orderId, BigDecimal amount);
}
