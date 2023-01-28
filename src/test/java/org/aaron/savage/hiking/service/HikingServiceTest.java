package org.aaron.savage.hiking.service;

import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.entity.*;
import org.aaron.savage.hiking.exception.DuplicateEntryException;
import org.aaron.savage.hiking.exception.FailedToSendEmailException;
import org.aaron.savage.hiking.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HikingServiceTest {

    private MountainRepository mountainRepository = mock(MountainRepository.class);

    private UserRepository userRepository = mock(UserRepository.class);

    private MunroBagRepository munroBagRepository = mock(MunroBagRepository.class);

    private TripRepository tripRepository = mock(TripRepository.class);

    private TripGroupRepository tripGroupRepository = mock(TripGroupRepository.class);

    private JavaMailSender emailSender = mock(JavaMailSender.class);

    private HikingService hikingService;

    @BeforeEach
    public void setUp() {
        hikingService = new HikingService(mountainRepository, userRepository, munroBagRepository, tripRepository,
                tripGroupRepository, emailSender);
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
                .setUsername("user67")
                .setPassword("securePassword")
                .setEmail("email@account.com")
                .setActivated("yes")
                .setCode("123456");
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
                .setOrganiserId(2)
                .setMountainId(14)
                .setDate("27/05/2000")
                .setDescription("Walk is taking place now");
    }

    private TripGroupEntity createTripGroupEntity() {

        return new TripGroupEntity()
                .setUsername("user67")
                .setTripId(6)
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
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
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
    public void testGetUserByPassword() {

        // arrange
        UserEntity userEntity = createUserEntity();
        UserDto expectedUserDto = createMatchingUserDto(userEntity);
        when(userRepository.findByPassword("securePassword")).thenReturn(userEntity);

        // act
        UserDto userDto = hikingService.getUserByPassword("securePassword");

        // assert
        assertThat(userDto).isEqualTo(expectedUserDto);
    }

    @Test
    public void testDuplicateCreateUser() {

        // arrange

        // act
        when(userRepository.save(any(UserEntity.class))).thenThrow(DuplicateEntryException.class);

        // assert
        assertThatExceptionOfType(DuplicateEntryException.class)
                .isThrownBy(() -> {
                    hikingService.createUser("user67", "securePassword", "email@account.com");
                });
    }

    @Test
    public void testEmailSendFailureOnCreateUser() {

        // arrange
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sender@mail.com");
        message.setTo("receiver@mail.com");
        message.setSubject("Subject");
        message.setText("Hello World!");

        // act
        doThrow(FailedToSendEmailException.class).when(emailSender).send(any(SimpleMailMessage.class));

        // assert
        assertThatExceptionOfType(FailedToSendEmailException.class)
                .isThrownBy(() -> {
                    hikingService.createUser("user67", "securePassword", "email@account.com");
                });
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

    @Test
    public void testGetTripByOrganiserId() {

        // arrange
        TripEntity tripEntity = createTripEntity();
        TripDto expectedTripDto = createMatchingTripDto(tripEntity);
        when(tripRepository.findByOrganiserId(2)).thenReturn(tripEntity);

        // act
        TripDto tripDto = hikingService.getTripByOrganiserId(2);

        // assert
        assertThat(tripDto).isEqualTo(expectedTripDto);
    }

    @Test
    public void testGetTripGroupByTripId() {

        // arrange
        TripGroupEntity tripGroupEntity = createTripGroupEntity();
        TripGroupDto expectedTripGroupDto = createMatchingTripGroupDto(tripGroupEntity);
        when(tripGroupRepository.findByTripId(6)).thenReturn(tripGroupEntity);

        // act
        TripGroupDto tripGroupDto = hikingService.getTripGroupByTripId(6);

        // assert
        assertThat(tripGroupDto).isEqualTo(expectedTripGroupDto);
    }
}