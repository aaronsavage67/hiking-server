package org.aaron.savage.hiking.controller;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.service.HikingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("hiking/")
@AllArgsConstructor
public class HikingController {

    private HikingService hikingService;

    @GetMapping("getMountains")
    public List<MountainDto> getMountains() {

        return hikingService.getMountains("name");
    }

    @GetMapping("getUsername")
    public UserDto getUsername() {

        return hikingService.getUsername("username");
    }

    @GetMapping("getMunrosBaggedByUsername")
    public List<MunroBagDto> getMunrosBaggedByUsername() {

        return hikingService.getMunrosBaggedByUsername("username");
    }

    @GetMapping("getTripByOrganiserId")
    public TripDto getTripByOrganiserId() {

        return hikingService.getTripByOrganiserId(12345678910L);
    }

    @GetMapping("getTripGroupByTripId")
    public TripGroupDto getTripGroupByTripId() {

        return hikingService.getTripGroupByTripId(12345678910L);
    }
}
