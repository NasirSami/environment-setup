package com.example.order;

import java.math.BigDecimal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final ShippingService shippingService;
    private final PaymentGateway paymentGateway;

    @Autowired
    public OrderService(@Qualifier("standardShippingService") ShippingService shippingService,
                        @Qualifier("creditPaymentGateway") PaymentGateway paymentGateway) {
        this.shippingService = shippingService;
        this.paymentGateway = paymentGateway;
    }

    @PostConstruct
    public void onInit() {
        System.out.println("[OrderService] Ready to process orders using "
                + shippingService.getClass().getSimpleName() + " and "
                + paymentGateway.getClass().getSimpleName());
    }

    public OrderSummary processOrder(OrderRequest request) {
        BigDecimal shippingCost = shippingService.calculateShippingCost(request);
        PaymentReceipt receipt = paymentGateway.charge(request.getOrderId(), request.getTotal().add(shippingCost));
        String shipmentDetails = shippingService.arrangeShipment(request);
        return new OrderSummary(request, shippingCost, receipt, shipmentDetails);
    }
}
