package com.example.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service("creditPaymentGateway")
public class CreditPaymentGateway implements PaymentGateway {

    private final String processorId = "GATEWAY-" + UUID.randomUUID();

    @PostConstruct
    public void initialize() {
        System.out.println("[CreditPaymentGateway] Connected with processor id " + processorId);
    }

    @Override
    public PaymentReceipt charge(String orderId, BigDecimal amount) {
        String confirmation = "PAY-" + Instant.now().toEpochMilli();
        String message = String.format("Charged %s for order %s using processor %s", amount, orderId, processorId);
        return new PaymentReceipt(confirmation, message);
    }
}
