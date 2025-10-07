package com.example.order;

public final class PaymentReceipt {

    private final String confirmationNumber;
    private final String message;

    public PaymentReceipt(String confirmationNumber, String message) {
        this.confirmationNumber = confirmationNumber;
        this.message = message;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PaymentReceipt{" +
                "confirmationNumber='" + confirmationNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
