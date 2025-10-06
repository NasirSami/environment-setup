package com.example.di;

/**
 * Email-based implementation of the message service.
 */
public class EmailService implements MessageService {

    private String recipientAddress;
    private String messageBody;

    public EmailService() {
        // Default constructor leaves room for setters to inject values later.
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
