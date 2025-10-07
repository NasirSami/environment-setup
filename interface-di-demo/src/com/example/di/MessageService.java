package com.example.di;

/**
 * Common contract for all message services used by the client.
 */
public interface MessageService {

    /**
     * Provides the message payload that will be delivered by the service.
     *
     * @return message text, never null
     */
    String getMessage();

    /**
     * Sends the message using the specific delivery mechanism.
     */
    void sendMessage();
}
