package com.example.di;

/**
 * SMS-based implementation of the message service.
 */
public class SmsService implements MessageService {

    private String phoneNumber;
    private String messageBody;

    public SmsService() {
        this.phoneNumber = "+15555551234";
        this.messageBody = "Switching to SMS simply replaces the injected service.";
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
