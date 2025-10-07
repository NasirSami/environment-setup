package com.example.di;

/**
 * Email-based implementation of the message service.
 */
public class EmailService implements MessageService {

    private String recipientAddress;
    private String messageBody;

    public EmailService() {
        this.recipientAddress = "support@example.com";
        this.messageBody = "Setter injection keeps this email loosely coupled.";
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String getMessage() {
        return "Email to " + recipientAddress + ": " + messageBody;
    }

    @Override
    public void sendMessage() {
        System.out.println("[EmailService] Sending -> " + getMessage());
    }
}
