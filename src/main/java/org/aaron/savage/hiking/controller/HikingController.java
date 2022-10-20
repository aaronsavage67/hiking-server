package org.aaron.savage.hiking.controller;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.MountainDto;
import org.aaron.savage.hiking.dto.MunroBagDto;
import org.aaron.savage.hiking.dto.UserDto;
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
}
