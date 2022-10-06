package org.aaron.savage.hiking.config;

import org.aaron.savage.hiking.service.HikingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikingConfiguration {

    @Bean
    HikingService hikingService() {
        return new HikingService();
    }

}
