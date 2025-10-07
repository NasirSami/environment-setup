package com.example.payment;

/**
 * Result returned after a payment has been processed.
 */
public final class PaymentReceipt {

    private final String confirmationCode;
    private final String message;
    private final String processedByInstance;

    public PaymentReceipt(String confirmationCode, String message, String processedByInstance) {
        this.confirmationCode = confirmationCode;
        this.message = message;
        this.processedByInstance = processedByInstance;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public String getMessage() {
        return message;
    }

    public String getProcessedByInstance() {
        return processedByInstance;
    }

    @Override
    public String toString() {
        return "PaymentReceipt{" +
                "confirmationCode='" + confirmationCode + '\'' +
                ", message='" + message + '\'' +
                ", processedByInstance='" + processedByInstance + '\'' +
                '}';
    }
}
