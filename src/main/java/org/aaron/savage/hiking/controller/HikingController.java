package org.aaron.savage.hiking.controller;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.HikingPlace;
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

    @GetMapping("info")
    public List<HikingPlace> getList() {
        return hikingService.getInfo();
    }

}
