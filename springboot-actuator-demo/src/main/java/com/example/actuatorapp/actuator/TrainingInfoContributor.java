package com.example.actuatorapp.actuator;

import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class TrainingInfoContributor implements InfoContributor {
    @Override
    public void contribute(Builder builder) {
        builder.withDetail("training", Map.of(
                "module", "Spring Boot Actuator Demo",
                "day", "2",
                "topics", List.of("Actuator", "DevTools", "YAML", "Lombok", "HTTP Status")
        ));
    }
}
