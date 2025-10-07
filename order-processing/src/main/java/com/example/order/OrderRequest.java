package com.example.order;

import java.math.BigDecimal;

/**
 * Represents an order coming into the system from a client (e.g. checkout page).
 */
public final class OrderRequest {

    private final String orderId;
    private final BigDecimal total;
    private final String destinationZip;

    public OrderRequest(String orderId, BigDecimal total, String destinationZip) {
        this.orderId = orderId;
        this.total = total;
        this.destinationZip = destinationZip;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getDestinationZip() {
        return destinationZip;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderId='" + orderId + '\'' +
                ", total=" + total +
                ", destinationZip='" + destinationZip + '\'' +
                '}';
    }
}
