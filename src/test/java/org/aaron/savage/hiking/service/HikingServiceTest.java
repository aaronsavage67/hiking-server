package org.aaron.savage.hiking.service;

import org.aaron.savage.hiking.dto.MountainDto;
import org.aaron.savage.hiking.dto.MunroBagDto;
import org.aaron.savage.hiking.dto.UserDto;
import org.aaron.savage.hiking.entity.MountainEntity;
import org.aaron.savage.hiking.entity.MunroBagEntity;
import org.aaron.savage.hiking.entity.UserEntity;
import org.aaron.savage.hiking.repository.MountainRepository;
import org.aaron.savage.hiking.repository.MunroBagRepository;
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

    private MunroBagRepository munroBagRepository = mock(MunroBagRepository.class);

    private HikingService hikingService;

    @BeforeEach
    public void setUp() {
        hikingService = new HikingService(mountainRepository, userRepository, munroBagRepository);
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

    private MunroBagEntity createMunroBagEntity() {

        return new MunroBagEntity()
                .setUsername("user67")
                .setDate("27/05/2000")
                .setRating("10/10")
                .setComments("what a great time I had climbing this hill");
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

    private MunroBagDto createMatchingMunroBagDto(MunroBagEntity munroBagEntity) {

        return MunroBagDto.builder()
                .username(munroBagEntity.getUsername())
                .date(munroBagEntity.getDate())
                .rating(munroBagEntity.getRating())
                .comments(munroBagEntity.getComments())
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

    @Test
    public void testGetMunrosBaggedByUsername() {

        //arrange
        MunroBagEntity munroBagEntity = createMunroBagEntity();
        MunroBagDto expectedMunroBagDto = createMatchingMunroBagDto(munroBagEntity);
        when(munroBagRepository.findByUsername("user67")).thenReturn(List.of(munroBagEntity));

        //act
        List<MunroBagDto> munroBagDto = hikingService.getMunrosBaggedByUsername("user67");

        //assert
        assertThat(munroBagDto).containsOnly(expectedMunroBagDto);
    }
}