package org.aaron.savage.hiking.service;

import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.entity.*;
import org.aaron.savage.hiking.repository.*;
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

    private TripRepository tripRepository = mock(TripRepository.class);

    private TripGroupRepository tripGroupRepository = mock(TripGroupRepository.class);

    private HikingService hikingService;

    @BeforeEach
    public void setUp() {
        hikingService = new HikingService(mountainRepository, userRepository, munroBagRepository, tripRepository,
                tripGroupRepository);
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

    private TripEntity createTripEntity() {

        return new TripEntity()
                .setOrganiserId(12345678910L)
                .setMountainId(12345678910L)
                .setDate("27/05/2000")
                .setDescription("Walk is taking place now");
    }

    private TripGroupEntity createTripGroupEntity() {

        return new TripGroupEntity()
                .setUsername("user67")
                .setTripId(12345678910L)
                .setStatus("Yes");
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

    private TripDto createMatchingTripDto(TripEntity tripEntity) {

        return TripDto.builder()
                .organiserId(tripEntity.getOrganiserId())
                .mountainId(tripEntity.getMountainId())
                .date(tripEntity.getDate())
                .description(tripEntity.getDescription())
                .build();
    }

    private TripGroupDto createMatchingTripGroupDto(TripGroupEntity tripGroupEntity) {

        return TripGroupDto.builder()
                .tripId(tripGroupEntity.getTripId())
                .username(tripGroupEntity.getUsername())
                .status(tripGroupEntity.getStatus())
                .build();
    }

    @Test
    public void testGetAllMountains() {

        // arrange
        MountainEntity mountainEntity = createMountainEntity();
        MountainDto expectedMountainDto = createMatchingMountainDto(mountainEntity);
        when(mountainRepository.findAll()).thenReturn(List.of(mountainEntity));

        // act
        List<MountainDto> mountainDtos = hikingService.getAllMountains();

        // assert
        assertThat(mountainDtos).containsOnly(expectedMountainDto);
    }

    @Test
    public void testGetMountainByName() {

        // arrange
        MountainEntity mountainEntity = createMountainEntity();
        MountainDto expectedMountainDto = createMatchingMountainDto(mountainEntity);
        when(mountainRepository.findByName("Ben Lui")).thenReturn(List.of(mountainEntity));

        // act
        List<MountainDto> mountainDtos = hikingService.getMountainByName("Ben Lui");

        // assert
        assertThat(mountainDtos).containsOnly(expectedMountainDto);
    }

    @Test
    public void testGetMountainById() {

        // arrange
        MountainEntity mountainEntity = createMountainEntity();
        MountainDto expectedMountainDto = createMatchingMountainDto(mountainEntity);
        when(mountainRepository.findById(35)).thenReturn(mountainEntity);

        // act
        MountainDto mountainDtos = hikingService.getMountainById(35);

        // assert
        assertThat(mountainDtos).isEqualTo(expectedMountainDto);
    }

    @Test
    public void testGetMountainsByRegion() {

        // arrange
        MountainEntity mountainEntity = createMountainEntity();
        MountainDto expectedMountainDto = createMatchingMountainDto(mountainEntity);
        when(mountainRepository.findByRegion("Fort William")).thenReturn(List.of(mountainEntity));

        // act
        List<MountainDto> mountainDtos = hikingService.getMountainsByRegion("Fort William");

        // assert
        assertThat(mountainDtos).containsOnly(expectedMountainDto);
    }


    @Test
    public void testGetUserByUsername() {

        // arrange
        UserEntity userEntity = createUserEntity();
        UserDto expectedUserDto = createMatchingUserDto(userEntity);
        when(userRepository.findByUsername("KieranTierney")).thenReturn(userEntity);

        // act
        UserDto userDto = hikingService.getUserByUsername("KieranTierney");

        // assert
        assertThat(userDto).isEqualTo(expectedUserDto);
    }

    @Test
    public void testGetMunrosBaggedByUsername() {

        //arrange
        MunroBagEntity munroBagEntity = createMunroBagEntity();
        MunroBagDto expectedMunroBagDto = createMatchingMunroBagDto(munroBagEntity);
        when(munroBagRepository.findMunroBagByUsername("user67")).thenReturn(List.of(munroBagEntity));

        //act
        List<MunroBagDto> munroBagDto = hikingService.getMunrosBaggedByUsername("user67");

        //assert
        assertThat(munroBagDto).containsOnly(expectedMunroBagDto);
    }

//    @Test
//    public void testGetTripByOrganiserId() {
//
//        // arrange
//        TripEntity tripEntity = createTripEntity();
//        TripDto expectedTripDto = createMatchingTripDto(tripEntity);
//        when(tripRepository.findByOrganiserId(12345678910L)).thenReturn(tripEntity);
//
//        // act
//        TripDto tripDto = hikingService.getTripByOrganiserId(12345678910L);
//
//        // assert
//        assertThat(tripDto).isEqualTo(expectedTripDto);
//    }
//
//    @Test
//    public void testGetTripGroupByTripId() {
//
//        // arrange
//        TripGroupEntity tripGroupEntity = createTripGroupEntity();
//        TripGroupDto expectedTripGroupDto = createMatchingTripGroupDto(tripGroupEntity);
//        when(tripGroupRepository.findByTripId(12345678910L)).thenReturn(tripGroupEntity);
//
//        // act
//        TripGroupDto tripGroupDto = hikingService.getTripGroupByTripId(12345678910L);
//
//        // assert
//        assertThat(tripGroupDto).isEqualTo(expectedTripGroupDto);
//    }
}