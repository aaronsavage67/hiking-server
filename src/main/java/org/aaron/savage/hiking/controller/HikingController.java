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

    @GetMapping("getAllMountains")
    public List<MountainDto> getAllMountains() {

        return hikingService.getAllMountains();
    }

    /**
     *
     * @param name
     * @return List<MountainDto> returns a list as there are cases where there are two mountains with the same name
     */
    @GetMapping("getMountainByName")
    public List<MountainDto> getMountainByName(String name) {

        return hikingService.getMountainByName(name);
    }

    @GetMapping("getMountainById")
    public MountainDto getMountainById(long id) {

        return hikingService.getMountainById(id);
    }

    @GetMapping("getMountainsByRegion")
    public List<MountainDto> getMountainsByRegion(String region) {

        return hikingService.getMountainsByRegion(region);
    }

    @GetMapping("getUserByUsername")
    public UserDto getUserByUsername(String username) {

        return hikingService.getUserByUsername(username);
    }

    @GetMapping("getMunrosBaggedByUsername")
    public List<MunroBagDto> getMunrosBaggedByUsername(String username) {

        return hikingService.getMunrosBaggedByUsername(username);
    }

//    @GetMapping("getTripByOrganiserId")
//    public TripDto getTripByOrganiserId() {
//
//        return hikingService.getTripByOrganiserId(110L);
//    }
//
//    @GetMapping("getTripGroupByTripId")
//    public TripGroupDto getTripGroupByTripId() {
//
//        return hikingService.getTripGroupByTripId(110L);
//    }
}
