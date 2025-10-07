# Setter-Based DI via XML Context

This project mirrors a Spring XML configuration flow without requiring external dependencies. It uses a lightweight `SimpleApplicationContext` to parse `config/applicationContext.xml`, instantiate beans, and apply setter injection.

## Highlights
- `MessageService` defines the shared abstraction.
- `EmailService` and `SmsService` provide channel-specific implementations with setters for their configuration.
- `SimpleApplicationContext` reads `applicationContext.xml`, creates beans, and resolves `value`/`ref` properties via reflection.
- `Application` boots the custom context, retrieves beans, and demonstrates swapping from email (wired in XML) to SMS at runtime.

## Run the Demo
From `assignments/interface-di-demo`:

```bash
mkdir -p out
javac -d out src/com/example/di/*.java
java -cp out com.example.di.Application
```

Console output shows the email message sent first (coming from XML wiring) followed by the SMS message after the client swaps implementations programmatically.
