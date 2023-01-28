package org.aaron.savage.hiking.controller;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.exception.UndefinedException;
import org.aaron.savage.hiking.service.HikingService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping("hiking/")
@AllArgsConstructor
public class HikingController {

    private HikingService hikingService;

    private void isStringParameterPresent(String s) {

        if (s == null) {
            throw new UndefinedException();
        }
    }

    private void isLongParameterPresent(Long l) {

        if (l == null) {
            throw new UndefinedException();
        }
    }

    @GetMapping("getAllMountains")
    public List<MountainDto> getAllMountains() {

        return hikingService.getAllMountains();
    }

    /**
     * @param name mountain name
     * @return List<MountainDto> returns a list as there are cases where there are two mountains with the same name
     */
    @GetMapping("getMountainByName")
    public List<MountainDto> getMountainByName(String name) {

        isStringParameterPresent(name);
        return hikingService.getMountainByName(name);
    }

    /**
     * @param id the unique mountain id
     * @return MountainDto returns a mountain using the id parameter
     */
    @GetMapping("getMountainById")
    public MountainDto getMountainById(Long id) {

        isLongParameterPresent(id);
        return hikingService.getMountainById(id);
    }

    @GetMapping("getMountainsByRegion")
    public List<MountainDto> getMountainsByRegion(String region) {

        isStringParameterPresent(region);
        return hikingService.getMountainsByRegion(region);
    }

    @GetMapping("getUserByUsername")
    public UserDto getUserByUsername(String username) {

        isStringParameterPresent(username);
        return hikingService.getUserByUsername(username);
    }

    @GetMapping("getUserByPassword")
    public UserDto getUserByPassword(String password) {

        isStringParameterPresent(password);
        return hikingService.getUserByPassword(password);
    }

    @PostMapping("createUser")
    public void createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        hikingService.createUser(username, password, email);
    }

    @PostMapping("validateUserCode")
    public void validateUserCode(@RequestParam String username, @RequestParam String code) {

        hikingService.validateUserCode(username, code);
    }

    @PostMapping("resendCode")
    public void resendCode(@RequestParam String username) {

        hikingService.resendCode(username);
    }

    @PostMapping("generateNewCode")
    public void generateNewCode(@RequestParam String username) {

        hikingService.generateNewCode(username);
    }

    @PostMapping("isUsernameActivated")
    public boolean isUsernameActivated(@RequestParam String username) {

        return hikingService.isUsernameActivated(username);
    }

    @PostMapping("resetPassword")
    public void resetPassword (@RequestParam String username, @RequestParam String newPassword,
                               @RequestParam String code) throws NoSuchAlgorithmException, InvalidKeySpecException {

        hikingService.resetPassword(username, newPassword, code);
    }

    @PostMapping("validateLogin")
    public boolean validateLogin (@RequestParam String username,@RequestParam String password) throws
            NoSuchAlgorithmException, InvalidKeySpecException {

        return hikingService.validateLogin(username, password);
    }

    @GetMapping("getMunrosBaggedByUsername")
    public List<MunroBagDto> getMunrosBaggedByUsername(String username) {

        isStringParameterPresent(username);
        return hikingService.getMunrosBaggedByUsername(username);
    }

    @GetMapping("getTripByOrganiserId")
    public TripDto getTripByOrganiserId(Long organiserId) {

        isLongParameterPresent(organiserId);
        return hikingService.getTripByOrganiserId(organiserId);
    }

    @GetMapping("getTripGroupByTripId")
    public TripGroupDto getTripGroupByTripId(Long tripId) {

        isLongParameterPresent(tripId);
        return hikingService.getTripGroupByTripId(tripId);
    }
}
