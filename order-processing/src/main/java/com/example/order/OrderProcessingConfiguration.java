package com.example.order;

import java.math.BigDecimal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.order")
public class OrderProcessingConfiguration {

    @Bean
    public OrderRequest sampleOrder() {
        return new OrderRequest("ORD-20241007", BigDecimal.valueOf(249.99), "94107");
    }
}
