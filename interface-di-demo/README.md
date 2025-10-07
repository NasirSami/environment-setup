# Setter-Based DI Demo (Spring + Maven)

This project demonstrates switching message services (Email vs. SMS) using Spring's XML configuration and setter-based dependency injection.

## Highlights
- `MessageService` defines the shared contract.
- `EmailService` and `SmsService` provide concrete implementations whose details are supplied via setters.
- `NotificationClient` depends on `MessageService` and receives the implementation from Spring.
- `applicationContext.xml` wires beans and initially injects the email implementation.
- `Application` loads the Spring context, sends a message via email, then swaps to SMS by retrieving another bean.

## Build & Run
From the project root (`interface-di-demo`):

```bash
mvn clean compile exec:java
```

You should see console output showing email delivery followed by SMS delivery, illustrating how setter injection enables easy swapping of implementations.
