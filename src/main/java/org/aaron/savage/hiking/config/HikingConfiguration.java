package org.aaron.savage.hiking.config;

import org.aaron.savage.hiking.repository.*;
import org.aaron.savage.hiking.service.HikingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikingConfiguration {

    @Bean
    HikingService hikingService(MountainRepository mountainRepository, UserRepository userRepository, MunroBagRepository
            munroBagRepository, TripRepository tripRepository, TripGroupRepository tripGroupRepository) {

        return new HikingService(mountainRepository, userRepository, munroBagRepository, tripRepository,
                tripGroupRepository);
    }
}
