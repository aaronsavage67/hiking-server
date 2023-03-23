package org.aaron.savage.hiking.service;

import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.entity.*;
import org.aaron.savage.hiking.exception.*;
import org.aaron.savage.hiking.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
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

    private ReviewRepository reviewRepository = mock(ReviewRepository.class);

    private JavaMailSender emailSender = mock(JavaMailSender.class);

    private HikingService hikingService;

    @BeforeEach
    public void setUp() {
        hikingService = new HikingService(mountainRepository, userRepository, munroBagRepository, tripRepository,
                tripGroupRepository, reviewRepository, emailSender);
    }

    private MountainEntity createMountainEntity() {

        return new MountainEntity()
                .setName("Ben Nevis")
                .setHeight("4411")
                .setDescription("the tallest Munro")
                .setRegion("Fort William")
                .setCoords("237824");
    }

    private UserEntity createUserEntity() {

        return new UserEntity()
                .setUsername("user67")
                .setPassword("1000:58d4540b74c9f36a7020c8e4d12c724c:da76bfb783dad38f8c44003e62314092d3920d2d4961d6aa1360ea8fea0bda3eb013e8153dd3660c88daf76ee7c6944cfa826ae6435006986c398bb76598f634")
                .setEmail("email@account.com")
                .setActivated("yes")
                .setCode("123456");
    }

    private MunroBagEntity createMunroBagEntity() {

        return new MunroBagEntity()
                .setUsername("user67")
                .setMountainId(110L)
                .setMountainName("Am Bodach")
                .setDate("27/05/2000")
                .setRating("4");
    }

    private TripEntity createTripEntity() {

        return new TripEntity()
                .setMountainId(14)
                .setMountainName("Ben Lomond")
                .setDate("27/05/2000");
    }

    private TripGroupEntity createTripGroupEntity() {

        return new TripGroupEntity()
                .setTripId(6)
                .setUsername("user67");
    }

    private ReviewEntity createReviewEntity() {

        return new ReviewEntity()
                .setUsername("aaron67")
                .setReviewDate("27/03/2023")
                .setMountainId(10L)
                .setMountainName("Ben Nevis")
                .setWalkDate("10/4/2018")
                .setRating("3")
                .setComment("it was average");
    }

    private MountainDto createMatchingMountainDto(MountainEntity mountainEntity) {

        return MountainDto.builder()
                .name(mountainEntity.getName())
                .height(mountainEntity.getHeight())
                .description(mountainEntity.getDescription())
                .region(mountainEntity.getRegion())
                .coords(mountainEntity.getCoords())
                .build();
    }

    private UserDto createMatchingUserDto(UserEntity userEntity) {

        return UserDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .activated(userEntity.getActivated())
                .build();
    }

    private MunroBagDto createMatchingMunroBagDto(MunroBagEntity munroBagEntity) {

        return MunroBagDto.builder()
                .username(munroBagEntity.getUsername())
                .mountainId(munroBagEntity.getMountainId())
                .mountainName(munroBagEntity.getMountainName())
                .date(munroBagEntity.getDate())
                .rating(munroBagEntity.getRating())
                .build();
    }

    private TripDto createMatchingTripDto(TripEntity tripEntity) {

        return TripDto.builder()
                .usernames(new ArrayList<>())
                .mountainId(tripEntity.getMountainId())
                .mountainName(tripEntity.getMountainName())
                .date(tripEntity.getDate())
                .build();
    }

    private TripGroupDto createMatchingTripGroupDto(TripGroupEntity tripGroupEntity) {

        return TripGroupDto.builder()
                .tripId(tripGroupEntity.getTripId())
                .username(tripGroupEntity.getUsername())
                .build();
    }

    private ReviewDto createMatchingReviewDto(ReviewEntity reviewEntity) {

        return ReviewDto.builder()
                .username(reviewEntity.getUsername())
                .reviewDate(reviewEntity.getReviewDate())
                .mountainId(reviewEntity.getMountainId())
                .mountainName(reviewEntity.getMountainName())
                .walkDate(reviewEntity.getWalkDate())
                .rating(reviewEntity.getRating())
                .comment(reviewEntity.getComment())
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

        // act
        doThrow(MailParseException.class).when(emailSender).send(any(SimpleMailMessage.class));

        // assert
        assertThatExceptionOfType(FailedToSendEmailException.class)
                .isThrownBy(() -> {
                    hikingService.createUser("user67", "securePassword", "email@account.com");
                });
    }

    @Test
    public void testValidateUserCode() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        // act
        hikingService.validateUserCode("user67", "123456");

        // assert
        verify(userRepository).save(userEntity);
    }

    @Test
    public void testValidateUserCodeUserCodeDoesNotMatch() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        // act
        when(userRepository.save(any(UserEntity.class))).thenThrow(UserCodeDoesNotMatchException.class);

        // assert
        assertThatExceptionOfType(UserCodeDoesNotMatchException.class)
                .isThrownBy(() -> {
                    hikingService.validateUserCode("user67", "000000");
                });
    }

    @Test
    public void testResendCode() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo("email@account.com");
        message.setSubject("Verification Code");
        message.setText("Hello from Hiking App! Your verification code is: 123456");

        //act
        hikingService.resendCode("user67");

        //assert
        verify(emailSender).send(message);
    }

    @Test
    public void testGenerateNewCode() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        //act
        hikingService.generateNewCode("user67");

        //assert
        assertThat(userEntity.getCode()).isNotEqualTo("123456");
    }

    @Test
    public void testIsUsernameActivated() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);
        HikingService hikingServiceSpy = Mockito.spy(new HikingService(mountainRepository, userRepository,
                munroBagRepository, tripRepository, tripGroupRepository, reviewRepository, emailSender));

        //act
        hikingService.isUsernameActivated("user67");
        hikingServiceSpy.generateNewCode("user67");

        //assert
        verify(hikingServiceSpy).generateNewCode("user67");
    }

    @Test
    public void testIsUsernameActivatedUserDoesNotExist() {

        // arrange

        //act

        //assert
        assertThatExceptionOfType(UserDoesNotExistException.class)
                .isThrownBy(() -> {
                    hikingService.isUsernameActivated("user67");
                });
    }

    @Test
    public void testIsUsernameActivatedUserNotActivated() {

        // arrange
        UserEntity userEntity = createUserEntity();
        userEntity.setActivated(null);
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        //act

        //assert
        assertThatExceptionOfType(UserNotActivatedException.class)
                .isThrownBy(() -> {
                    hikingService.isUsernameActivated("user67");
                });
    }

    @Test
    public void testResetPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        // act
        hikingService.resetPassword("user67", "newPassword", "123456");

        // assert
        verify(userRepository).save(userEntity);
    }

    @Test
    public void testResetPasswordUserCodeDoesNotMatch() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        // act

        // assert
        assertThatExceptionOfType(UserCodeDoesNotMatchException.class)
                .isThrownBy(() -> {
                    hikingService.resetPassword("user67", "newPassword", "000000");
                });
    }

    @Test
    public void testValidateLoginTrue() throws NoSuchAlgorithmException, InvalidKeySpecException {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        // act

        // assert
        assertThat(hikingService.validateLogin("user67", "securePassword")).isTrue();
    }

    @Test
    public void testValidateLoginFalse() throws NoSuchAlgorithmException, InvalidKeySpecException {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);

        // act

        // assert
        assertThat(hikingService.validateLogin("user67", "incorrectPassword")).isFalse();
    }

    @Test
    public void testValidateLoginUserNotActivated() {

        // arrange
        UserEntity userEntity = createUserEntity();
        when(userRepository.findByUsername("user67")).thenReturn(userEntity);
        userEntity.setActivated(null);

        // act

        // assert
        assertThatExceptionOfType(UserNotActivatedException.class)
                .isThrownBy(() -> {
                    hikingService.validateLogin("user67", "securePassword");
                });
    }

    @Test
    public void testValidateLoginUserDoesNotExist() {

        // arrange

        // act

        // assert
        assertThatExceptionOfType(UserDoesNotExistException.class)
                .isThrownBy(() -> {
                    hikingService.validateLogin("userDoesNotExist", "securePassword");
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
    public void testAddMunroToBagDuplicateEntry() {

        // arrange
        MunroBagEntity munroBagEntity = createMunroBagEntity();
        MunroBagDto munroBagDto = createMatchingMunroBagDto(munroBagEntity);
        when(munroBagRepository.findMunroBagByUsername(munroBagDto.getUsername())).thenReturn(List.of(munroBagEntity));

        // act
        when(munroBagRepository.save(any(MunroBagEntity.class))).thenThrow(DuplicateEntryException.class);

        // assert
        assertThatExceptionOfType(DuplicateEntryException.class)
                .isThrownBy(() -> {
                    hikingService.addMunroToBag(munroBagDto);
                });
    }

    @Test
    public void testGetAllTrips() {

        // arrange
        TripEntity tripEntity = createTripEntity();
        TripDto expectedTripDto = createMatchingTripDto(tripEntity);
        when(tripRepository.findAll()).thenReturn(List.of(tripEntity));

        // act
        List<TripDto> tripDtos = hikingService.getAllTrips();

        // assert
        assertThat(tripDtos).containsOnly(expectedTripDto);
    }

    @Test
    public void testAddUserToTripDuplicateEntry() {

        // arrange
        TripEntity tripEntity = createTripEntity();
        TripDto tripDto = createMatchingTripDto(tripEntity);
        String username = "HashTest9";

        // act
        when(tripGroupRepository.save(any(TripGroupEntity.class))).thenThrow(DuplicateEntryException.class);

        // assert
        assertThatExceptionOfType(DuplicateEntryException.class)
                .isThrownBy(() -> {
                    hikingService.addUserToTrip(username, tripDto);
                });
    }

    @Test
    public void testGetIdByUsernameAndTripId() {

        // arrange
        TripGroupEntity tripGroupEntity = createTripGroupEntity();
        TripGroupDto expectedTripGroupDto = createMatchingTripGroupDto(tripGroupEntity);
        when(tripGroupRepository.findIdByUsernameAndTripId("user67", 6)).thenReturn(tripGroupEntity);

        // act
        TripGroupDto tripGroupDto = hikingService.getIdByUsernameAndTripId("user67", 6);

        // assert
        assertThat(tripGroupDto).isEqualTo(expectedTripGroupDto);
    }

    @Test
    public void testGetTripsByMountainName() {

        // arrange
        TripEntity tripEntity = createTripEntity();
        TripDto expectedTripDto = createMatchingTripDto(tripEntity);
        when(tripRepository.findTripsByMountainName("Ben Lomond")).thenReturn(List.of(tripEntity));

        // act
        List<TripDto> tripDtos = hikingService.getTripsByMountainName("Ben Lomond");

        // assert
        assertThat(tripDtos).containsOnly(expectedTripDto);
    }

    @Test
    public void testGetTripsByDate() {

        // arrange
        TripEntity tripEntity = createTripEntity();
        TripDto expectedTripDto = createMatchingTripDto(tripEntity);
        when(tripRepository.findTripsByDate("27/05/2000")).thenReturn(List.of(tripEntity));

        // act
        List<TripDto> tripDtos = hikingService.getTripsByDate("27/05/2000");

        // assert
        assertThat(tripDtos).containsOnly(expectedTripDto);
    }

    @Test
    public void testCreateTripDuplicateEntry() {

        // arrange
        TripEntity tripEntity = createTripEntity();
        TripDto tripDto = createMatchingTripDto(tripEntity);
        String username = "HashTest9";

        // act
        when(tripRepository.save(any(TripEntity.class))).thenThrow(DuplicateEntryException.class);

        // assert
        assertThatExceptionOfType(DuplicateEntryException.class)
                .isThrownBy(() -> {
                    hikingService.createTrip(username, tripDto);
                });
    }

    @Test
    public void testGetAllReviews() {

        // arrange
        ReviewEntity reviewEntity = createReviewEntity();
        ReviewDto expectedReviewDto = createMatchingReviewDto(reviewEntity);
        when(reviewRepository.findAll()).thenReturn(List.of(reviewEntity));

        // act
        List<ReviewDto> reviewDtos = hikingService.getAllReviews();

        // assert
        assertThat(reviewDtos).containsOnly(expectedReviewDto);
    }

    @Test
    public void testGetReviewsByMountainName() {

        // arrange
        ReviewEntity reviewEntity = createReviewEntity();
        ReviewDto expectedReviewDto = createMatchingReviewDto(reviewEntity);
        when(reviewRepository.findReviewsByMountainName("Ben Nevis")).thenReturn(List.of(reviewEntity));

        // act
        List<ReviewDto> reviewDtos = hikingService.getReviewsByMountainName("Ben Nevis");

        // assert
        assertThat(reviewDtos).containsOnly(expectedReviewDto);
    }
}