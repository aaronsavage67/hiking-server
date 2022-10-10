package org.aaron.savage.hiking.service;

import org.aaron.savage.hiking.dto.MountainDto;
import org.aaron.savage.hiking.dto.UserDto;
import org.aaron.savage.hiking.entity.MountainEntity;
import org.aaron.savage.hiking.entity.UserEntity;
import org.aaron.savage.hiking.repository.MountainRepository;
import org.aaron.savage.hiking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HikingServiceTest {

    private MountainRepository mountainRepository = mock(MountainRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private HikingService hikingService;

    @BeforeEach
    public void setUp() {
        hikingService = new HikingService(mountainRepository, userRepository);
    }

    private MountainEntity createMountainEntity() {

        return new MountainEntity()
                .setName("Ben Nevis")
                .setHeight("4411")
                .setDescription("the tallest Munro")
                .setRegion("Fort William")
                .setCoords("237824")
                .setRouteImage("image");
    }

    private UserEntity createUserEntity() {

        return new UserEntity()
                .setName("Aaron Savage")
                .setUsername("user67")
                .setPassword("securePassword");
    }

    private MountainDto createMatchingMountainDto(MountainEntity mountainEntity) {

        return MountainDto.builder()
                .name(mountainEntity.getName())
                .height(mountainEntity.getHeight())
                .description(mountainEntity.getDescription())
                .region(mountainEntity.getRegion())
                .coords(mountainEntity.getCoords())
                .routeImage(mountainEntity.getRouteImage())
                .build();
    }

    private UserDto createMatchingUserDto(UserEntity userEntity) {

        return UserDto.builder()
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    @Test
    public void testGetMountains() {

        // arrange
        MountainEntity mountainEntity = createMountainEntity();
        MountainDto expectedMountainDto = createMatchingMountainDto(mountainEntity);
        when(mountainRepository.findByName("Ben Lui")).thenReturn(List.of(mountainEntity));

        // act
        List<MountainDto> mountainDtos = hikingService.getMountains("Ben Lui");

        // assert
        assertThat(mountainDtos).containsOnly(expectedMountainDto);
    }

    @Test
    public void testGetUsername() {

        // arrange
        UserEntity userEntity = createUserEntity();
        UserDto expectedUserDto = createMatchingUserDto(userEntity);
        when(userRepository.findByUsername("KieranTierney")).thenReturn(userEntity);

        // act
        UserDto userDto = hikingService.getUsername("KieranTierney");

        // assert
        assertThat(userDto).isEqualTo(expectedUserDto);
    }
}