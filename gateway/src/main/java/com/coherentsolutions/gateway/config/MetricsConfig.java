package com.coherentsolutions.gateway.config;

import io.micrometer.core.instrument.config.MeterFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterFilter meterFilter() {
        return MeterFilter.deny(id -> {
            String uri = id.getTag("uri");
            return uri != null && (uri.startsWith("/actuator") || uri.contains("favicon.ico"));
        });
    }
}
