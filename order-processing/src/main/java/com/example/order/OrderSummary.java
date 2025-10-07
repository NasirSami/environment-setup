package com.example.order;

import java.math.BigDecimal;

public final class OrderSummary {

    private final OrderRequest orderRequest;
    private final BigDecimal shippingCost;
    private final PaymentReceipt paymentReceipt;
    private final String shipmentStatus;

    public OrderSummary(OrderRequest orderRequest,
                        BigDecimal shippingCost,
                        PaymentReceipt paymentReceipt,
                        String shipmentStatus) {
        this.orderRequest = orderRequest;
        this.shippingCost = shippingCost;
        this.paymentReceipt = paymentReceipt;
        this.shipmentStatus = shipmentStatus;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public PaymentReceipt getPaymentReceipt() {
        return paymentReceipt;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    @Override
    public String toString() {
        return "OrderSummary{" +
                "orderRequest=" + orderRequest +
                ", shippingCost=" + shippingCost +
                ", paymentReceipt=" + paymentReceipt +
                ", shipmentStatus='" + shipmentStatus + '\'' +
                '}';
    }
}
