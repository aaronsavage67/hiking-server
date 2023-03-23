package org.aaron.savage.hiking.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.service.HikingService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping("hiking/")
@AllArgsConstructor
@Slf4j
public class HikingController {

    private HikingService hikingService;

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam String munro) throws IOException {

        log.debug("/getImage received: {}", munro);
        InputStream in = getClass().getResourceAsStream("/images/" + munro + ".jpeg");
        byte[] byteArray = IOUtils.toByteArray(in);
        return byteArray;
    }

    @GetMapping("getAllMountains")
    public List<MountainDto> getAllMountains() {

        List<MountainDto> mountainDtos = hikingService.getAllMountains();
        log.debug("/getAllMountains returned: {}", mountainDtos);
        return mountainDtos;
    }

    @GetMapping("getMountainByName")
    public List<MountainDto> getMountainByName(@RequestParam String name) {

        log.debug("/getMountainByName received: {}", name);
        List<MountainDto> mountainDtos = hikingService.getMountainByName(name);
        log.debug("/getMountainByName returned: {}", mountainDtos);
        return mountainDtos;
    }

    @GetMapping("getMountainById")
    public MountainDto getMountainById(@RequestParam Long id) {

        log.debug("/getMountainById received: {}", id);
        MountainDto mountainDto = hikingService.getMountainById(id);
        log.debug("/getMountainById returned: {}", mountainDto);
        return mountainDto;
    }

    @GetMapping("getMountainsByRegion")
    public List<MountainDto> getMountainsByRegion(@RequestParam String region) {

        log.debug("/getMountainsByRegion received: {}", region);
        List<MountainDto> mountainDtos = hikingService.getMountainsByRegion(region);
        log.debug("/getMountainsByRegion returned: {}", mountainDtos);
        return mountainDtos;
    }

    @GetMapping("getUserByUsername")
    public UserDto getUserByUsername(@RequestParam String username) {

        log.debug("/getUserByUsername received: {}", username);
        UserDto userDto = hikingService.getUserByUsername(username);
        log.debug("/getUserByUsername returned: {}", userDto);
        return userDto;
    }

    @GetMapping("getUserByPassword")
    public UserDto getUserByPassword(@RequestParam String password) {

        log.debug("/getUserByPassword received: {}", password);
        UserDto userDto = hikingService.getUserByPassword(password);
        log.debug("/getUserByPassword returned: {}", userDto);
        return userDto;
    }

    @PostMapping("createUser")
    public void createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        log.debug("/createUser received: {}, {}, {}", username, password, email);
        hikingService.createUser(username, password, email);
    }

    @PostMapping("validateUserCode")
    public void validateUserCode(@RequestParam String username, @RequestParam String code) {

        log.debug("/validateUserCode received: {}, {}", username, code);
        hikingService.validateUserCode(username, code);
    }

    @PostMapping("resendCode")
    public void resendCode(@RequestParam String username) {

        log.debug("/resendCode received: {}", username);
        hikingService.resendCode(username);
    }

    @PostMapping("generateNewCode")
    public void generateNewCode(@RequestParam String username) {

        log.debug("/generateNewCode received: {}", username);
        hikingService.generateNewCode(username);
    }

    @PostMapping("isUsernameActivated")
    public void isUsernameActivated(@RequestParam String username) {

        log.debug("/isUsernameActivated received: {}", username);
        hikingService.isUsernameActivated(username);
    }

    @PostMapping("resetPassword")
    public void resetPassword(@RequestParam String username, @RequestParam String newPassword,
                              @RequestParam String code) throws NoSuchAlgorithmException, InvalidKeySpecException {

        log.debug("/resetPassword received: {}, {}, {}", username, newPassword, code);
        hikingService.resetPassword(username, newPassword, code);
    }

    @PostMapping("validateLogin")
    public boolean validateLogin(@RequestParam String username, @RequestParam String password) throws
            NoSuchAlgorithmException, InvalidKeySpecException {

        log.debug("/validateLogin received: {}, {}", username, password);
        boolean allowAccess = hikingService.validateLogin(username, password);
        log.debug("/validateLogin returned: {}", allowAccess);
        return allowAccess;
    }

    @GetMapping("getMunrosBaggedByUsername")
    public List<MunroBagDto> getMunrosBaggedByUsername(@RequestParam String username) {

        log.debug("/getMunrosBaggedByUsername received: {}", username);
        List<MunroBagDto> munroBagDtos = hikingService.getMunrosBaggedByUsername(username);
        log.debug("/getMunrosBaggedByUsername returned: {}", munroBagDtos);
        return munroBagDtos;
    }

    @PostMapping("addMunroToBag")
    public List<MunroBagDto> addMunroToBag(@RequestBody MunroBagDto munroBagDto) {

        log.debug("/addMunroToBag received: {}", munroBagDto);
        List<MunroBagDto> munroBagDtos = hikingService.addMunroToBag(munroBagDto);
        log.debug("/addMunroToBag returned: {}", munroBagDtos);
        return munroBagDtos;
    }

    @PostMapping("removeMunroFromBag")
    public List<MunroBagDto> removeMunroFromBag(@RequestBody MunroBagDto munroBagDto) {

        log.debug("/removeMunroFromBag received: {}", munroBagDto);
        List<MunroBagDto> munroBagDtos = hikingService.removeMunroFromBag(munroBagDto);
        log.debug("/removeMunroFromBag returned: {}", munroBagDtos);
        return munroBagDtos;
    }

    @GetMapping("getAllTrips")
    public List<TripDto> getAllTrips() {

        List<TripDto> tripDtos = hikingService.getAllTrips();
        log.debug("/getAllTrips returned: {}", tripDtos);
        return tripDtos;
    }

    @PostMapping("addUserToTrip")
    public List<TripDto> addUserToTrip(@RequestParam String username, @RequestBody TripDto tripDto) {

        log.debug("/addUserToTrip received: {}, {}", username, tripDto);
        List<TripDto> tripDtos = hikingService.addUserToTrip(username, tripDto);
        log.debug("/addUserToTrip returned: {}", tripDtos);
        return tripDtos;
    }

    @PostMapping("removeUserFromTrip")
    public List<TripDto> removeUserFromTrip(@RequestParam String username, @RequestBody TripDto tripDto) {

        log.debug("/removeUserFromTrip received: {}, {}", username, tripDto);
        List<TripDto> tripDtos = hikingService.removeUserFromTrip(username, tripDto);
        log.debug("/removeUserFromTrip returned: {}", tripDtos);
        return tripDtos;
    }

    @GetMapping("getTripsByMountainName")
    public List<TripDto> getTripsByMountainName(@RequestParam String mountainName) {

        log.debug("/getTripsByMountainName received: {}", mountainName);
        List<TripDto> tripDtos = hikingService.getTripsByMountainName(mountainName);
        log.debug("/getTripsByMountainName returned: {}", tripDtos);
        return tripDtos;
    }

    @GetMapping("getTripsByDate")
    public List<TripDto> getTripsByDate(@RequestParam String date) {

        log.debug("/getTripsByDate received: {}", date);
        List<TripDto> tripDtos = hikingService.getTripsByDate(date);
        log.debug("/getTripsByDate returned: {}", tripDtos);
        return tripDtos;
    }

    @PostMapping("createTrip")
    public List<TripDto> createTrip(@RequestParam String username, @RequestBody TripDto tripDto) {

        log.debug("/createTrip received: {}, {}", username, tripDto);
        List<TripDto> tripDtos = hikingService.createTrip(username, tripDto);
        log.debug("/createTrip returned: {}", tripDtos);
        return tripDtos;
    }

    @GetMapping("getAllReviews")
    public List<ReviewDto> getAllReviews() {

        List<ReviewDto> reviewDtos = hikingService.getAllReviews();
        log.debug("/getAllReviews returned: {}", reviewDtos);
        return reviewDtos;
    }

    @PostMapping("createReview")
    public List<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {

        log.debug("/createReview received: {}", reviewDto);
        List<ReviewDto> reviewDtos = hikingService.createReview(reviewDto);
        log.debug("/createReview returned: {}", reviewDtos);
        return reviewDtos;
    }

    @GetMapping("getReviewsByMountainName")
    public List<ReviewDto> getReviewsByMountainName(String name) {

        log.debug("/getReviewsByMountainName received: {}", name);
        List<ReviewDto> reviewDtos = hikingService.getReviewsByMountainName(name);
        log.debug("/getReviewsByMountainName returned: {}", reviewDtos);
        return reviewDtos;
    }
}
