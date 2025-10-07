package com.example.order;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class OrderProcessingRunner {

    private OrderProcessingRunner() {
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(OrderProcessingConfiguration.class)) {

            OrderRequest request = context.getBean(OrderRequest.class);
            OrderService orderService = context.getBean(OrderService.class);

            OrderSummary summary = orderService.processOrder(request);

            System.out.println("Order processed successfully.");
            System.out.println("Shipping cost: " + summary.getShippingCost());
            System.out.println("Payment receipt: " + summary.getPaymentReceipt());
            System.out.println("Shipment status: " + summary.getShipmentStatus());
        }
    }
}
