package org.aaron.savage.hiking.service;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.HikingPlace;

import java.util.List;

@AllArgsConstructor
public class HikingService {

    public List<HikingPlace> getInfo() {
        return List.of(
                HikingPlace.builder()
                        .name("bob")
                        .description("a place")
                        .build()
        );
    }
}
