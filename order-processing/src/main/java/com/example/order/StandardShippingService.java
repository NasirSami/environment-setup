package com.example.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service("standardShippingService")
public class StandardShippingService implements ShippingService {

    private final String carrierReference = "SHIP-" + UUID.randomUUID();

    @PostConstruct
    public void init() {
        System.out.println("[StandardShippingService] Initialized carrier reference " + carrierReference);
    }

    @Override
    public BigDecimal calculateShippingCost(OrderRequest orderRequest) {
        BigDecimal base = new BigDecimal("7.50");
        int distanceFactor = orderRequest.getDestinationZip().startsWith("9") ? 5 : 3;
        return base.multiply(BigDecimal.valueOf(distanceFactor)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String arrangeShipment(OrderRequest orderRequest) {
        return "Shipment scheduled via carrier " + carrierReference + " for order " + orderRequest.getOrderId();
    }
}
