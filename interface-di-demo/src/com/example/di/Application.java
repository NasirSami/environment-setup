package com.example.di;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        String configLocation = "config/applicationContext.xml";
        SimpleApplicationContext context = new SimpleApplicationContext(configLocation);

        NotificationClient client = context.getBean("notificationClient", NotificationClient.class);
        client.sendCurrentMessage();

        MessageService smsService = context.getBean("smsService", MessageService.class);
        client.setMessageService(smsService);
        client.sendCurrentMessage();
    }
}
