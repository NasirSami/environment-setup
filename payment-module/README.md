# Payment Module (Spring XML Assignment)

This assignment demonstrates how to use Spring's IoC container to inject different payment implementations into a checkout workflow while showcasing bean scopes.

## Features
- `PaymentService` interface with mock implementations for credit card (singleton scope) and PayPal (prototype scope).
- Setter-based dependency injection via XML configuration (`applicationContext.xml`).
- `PaymentProcessor` bean that accepts whichever `PaymentService` Spring injects, illustrating plug-and-play strategies.
- `PaymentModuleRunner` boots a `ClassPathXmlApplicationContext`, processes a payment with the credit card implementation, then retrieves two PayPal processors to highlight prototype scope creating new bean instances.

## Requirements
- Java 17+
- Maven 3.8+

## Run the Demo
From this directory (`payment-module`):

```bash
mvn clean compile exec:java
```

The console output will show receipts for the credit card and PayPal flows along with instance identifiers, making the bean scope differences visible.
