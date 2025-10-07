package com.example.order;

import java.math.BigDecimal;

/**
 * Calculates shipping costs and prepares shipments for outgoing orders.
 */
public interface ShippingService {

    BigDecimal calculateShippingCost(OrderRequest orderRequest);

    String arrangeShipment(OrderRequest orderRequest);
}
