package com.example.payment;

/**
 * Contract for payment strategies supported by the module.
 */
public interface PaymentService {

    /**
     * @return descriptive name of the payment method (e.g. "Credit Card")
     */
    String getName();

    /**
     * Processes the given payment request and returns a receipt with confirmation details.
     */
    PaymentReceipt process(PaymentRequest request);
}
