package com.example.di;

/**
 * Client class that depends on a {@link MessageService}. The dependency is supplied
 * via a setter method to demonstrate setter-based injection.
 */
public class NotificationClient {

    private MessageService messageService;

    /**
     * Setter-based injection point. In a real Spring application this method would be
     * called by the container; here we call it manually to keep the example lightweight.
     */
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendCurrentMessage() {
        if (messageService == null) {
            throw new IllegalStateException("MessageService has not been injected.");
        }
        messageService.sendMessage();
    }
}
