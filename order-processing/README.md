# Order Processing System (Spring Assignment)

Demonstrates how Spring manages dependencies between `OrderService`, `ShippingService`, and `PaymentGateway`. `OrderService` orchestrates an order by calculating shipping, charging payment, and scheduling shipment.

## Highlights
- Annotation-based configuration (`OrderProcessingConfiguration`) registers the sample `OrderRequest` and enables component scanning for services.
- `OrderService` uses constructor injection (`@Autowired` + `@Qualifier`) to receive both `ShippingService` and `PaymentGateway` implementations.
- `StandardShippingService` and `CreditPaymentGateway` use `@Service` stereotypes and `@PostConstruct` hooks for initialization logging.
- Lifecycle callbacks in `OrderService` confirm when dependencies are ready.

## Run
From this module (`order-processing`):

```bash
mvn clean compile exec:java
```

The console output will show initialization messages followed by the processed order summary (shipping cost, payment receipt, shipment status).
