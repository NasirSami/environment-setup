package com.example.di;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("applicationContext.xml")) {
            NotificationClient client = context.getBean("notificationClient", NotificationClient.class);
            client.sendCurrentMessage();

            MessageService smsService = context.getBean("smsService", MessageService.class);
            client.setMessageService(smsService);
            client.sendCurrentMessage();
        }
    }
}
