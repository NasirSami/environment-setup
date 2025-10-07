package com.example.di;

/**
 * SMS-based implementation of the message service.
 */
public class SmsService implements MessageService {

    private String phoneNumber;
    private String messageBody;

    public SmsService() {
        // Default constructor keeps the class compatible with setter injection.
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String getMessage() {
        return "SMS to " + phoneNumber + ": " + messageBody;
    }

    @Override
    public void sendMessage() {
        System.out.println("[SmsService] Sending -> " + getMessage());
    }
}
