package com.example.di;

/**
 * Demonstrates switching implementations of {@link MessageService} by injecting them
 * into {@link NotificationClient} through a setter.
 */
public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        NotificationClient client = new NotificationClient();

        EmailService emailService = new EmailService();
        emailService.setRecipientAddress("support@example.com");
        emailService.setMessageBody("Setter injection keeps this email loosely coupled.");
        client.setMessageService(emailService);
        client.sendCurrentMessage();

        SmsService smsService = new SmsService();
        smsService.setPhoneNumber("+15555551234");
        smsService.setMessageBody("Switching to SMS simply replaces the injected service.");
        client.setMessageService(smsService);
        client.sendCurrentMessage();
    }
}
