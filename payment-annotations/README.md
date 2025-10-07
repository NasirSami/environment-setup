# Payment Module (Annotation-Based)

Annotation-driven variant of the payment assignment. Uses `@Configuration`, `@ComponentScan`, and stereotype annotations such as `@Service` to replace the XML wiring while keeping the business logic identical.

## Highlights
- `PaymentModuleConfiguration` enables component scanning and declares only the necessary factory beans (`PaymentRequest`, two `PaymentProcessor` instances) while Spring discovers service implementations automatically.
- `@Service` marks the payment strategies for component scanning. `@Qualifier` in the configuration chooses which service backs each processor.
- `@PostConstruct` and `@PreDestroy` lifecycle callbacks log when services/processors are initialized or shut down.
- `PaymentModuleRunner` loads the configuration via `AnnotationConfigApplicationContext` and exercises the payment flow.

## Run
From this directory:

```bash
mvn clean compile exec:java
```

Expect the same output as the XML module: credit-card receipt followed by two PayPal receipts with different instance identifiers.
