package org.aaron.savage.hiking.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.entity.*;
import org.aaron.savage.hiking.exception.*;
import org.aaron.savage.hiking.repository.*;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Slf4j
public class HikingService {

    MountainRepository mountainRepository;

    UserRepository userRepository;

    MunroBagRepository munroBagRepository;

    TripRepository tripRepository;

    TripGroupRepository tripGroupRepository;

    ReviewRepository reviewRepository;

    JavaMailSender emailSender;

    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 64 * 8;
    private static final int BOUND = 999999;
    private static final String EMAIL_FROM = "noreply@gmail.com";
    private static final String EMAIL_SUBJECT = "Verification Code";
    private static final String EMAIL_TEXT = "Hello from Hiking App! Your verification code is: ";

    public List<MountainDto> getAllMountains() {

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        List<MountainDto> mountainDtos = mountainEntities.stream()
                .map(entity -> MountainDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .height(entity.getHeight())
                        .coords(entity.getCoords())
                        .region(entity.getRegion())
                        .build()
                )
                .collect(Collectors.toList());

        log.debug("getAllMountains() returned: {}", mountainDtos);
        return mountainDtos;
    }

    public List<MountainDto> getMountainByName(String name) {

        log.debug("getMountainByName() received: {}", name);

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findByName(name).spliterator(),
                false).collect(Collectors.toList());

        List<MountainDto> mountainDtos = mountainEntities.stream()
                .map(entity -> MountainDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .height(entity.getHeight())
                        .coords(entity.getCoords())
                        .region(entity.getRegion())
                        .build()
                )
                .collect(Collectors.toList());

        log.debug("getMountainByName() returned: {}", mountainDtos);
        return mountainDtos;
    }

    public MountainDto getMountainById(long id) {

        log.debug("getMountainById() received: {}", id);

        MountainEntity mountainEntity = mountainRepository.findById(id);

        MountainDto mountainDto = MountainDto.builder()
                .id(mountainEntity.getId())
                .name(mountainEntity.getName())
                .description(mountainEntity.getDescription())
                .height(mountainEntity.getHeight())
                .coords(mountainEntity.getCoords())
                .region(mountainEntity.getRegion())
                .build();

        log.debug("getMountainById() returned: {}", mountainDto);
        return mountainDto;
    }

    public List<MountainDto> getMountainsByRegion(String region) {

        log.debug("getMountainsByRegion() received: {}", region);

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findByRegion(region)
                .spliterator(), false).collect(Collectors.toList());

        List<MountainDto> mountainDtos = mountainEntities.stream()
                .map(entity -> MountainDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .height(entity.getHeight())
                        .coords(entity.getCoords())
                        .region(entity.getRegion())
                        .build()
                )
                .collect(Collectors.toList());

        log.debug("getMountainsByRegion() returned: {}", mountainDtos);
        return mountainDtos;
    }

    public UserDto getUserByUsername(String username) {

        log.debug("getUserByUsername() received: {}", username);

        UserEntity userEntity = userRepository.findByUsername(username);

        UserDto userDto = UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .activated(userEntity.getActivated())
                .build();

        log.debug("getUserByUsername() returned: {}", userDto);
        return userDto;
    }

    public UserDto getUserByPassword(String password) {

        log.debug("getUserByPassword() received: {}", password);

        UserEntity userEntity = userRepository.findByPassword(password);

        UserDto userDto = UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .activated(userEntity.getActivated())
                .build();

        log.debug("getUserByPassword() returned: {}", userDto);
        return userDto;
    }

    private String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        log.debug("generatePasswordHash() received: {}", password);

        char[] chars = password.toCharArray();
        byte[] salt = generateSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        String passwordHash = ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);

        log.debug("generatePasswordHash() returned: {}", passwordHash);
        return passwordHash;
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        log.debug("generateSalt() returned: {}", salt);
        return salt;
    }

    private String toHex(byte[] array) {

        log.debug("toHex() received: {}", array);

        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            String s = String.format("%0" + paddingLength + "d", 0) + hex;
            log.debug("toHex() returned: {}", s);
            return s;
        } else {
            log.debug("toHex() returned: {}", hex);
            return hex;
        }
    }

    public void createUser(String username, String password, String email) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        log.debug("createUser() received: {}, {}, {}", username, password, email);

        UserEntity userEntity = new UserEntity();

        String generatedSecuredPasswordHash = generatePasswordHash(password);

        String code = generateVerificationCode();

        userEntity.setUsername(username).setPassword(generatedSecuredPasswordHash).setEmail(email).setCode(code);

        try {
            userRepository.save(userEntity);
            sendEmail(email, code);
            log.info("User created with username: {}", username);
        } catch (MailParseException m) {
            log.error("Failed to send email to: {}", email);
            throw new FailedToSendEmailException();
        } catch (DataAccessException e) {
            log.error("Failed to create user as username: {}, already exists", username);
            throw new DuplicateEntryException();
        }
    }

    public void sendEmail(String receiver, String code) {

        log.debug("sendEmail() received: {}, {}", receiver, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_FROM);
        message.setTo(receiver);
        message.setSubject(EMAIL_SUBJECT);
        message.setText(EMAIL_TEXT + code);
        emailSender.send(message);

        log.info("Email with code: {}, sent to : {}", code, receiver);
    }

    private String generateVerificationCode() {

        Random rnd = new Random();
        int number = rnd.nextInt(BOUND);
        String s = String.format("%06d", number);

        log.debug("generateVerificationCode() returned: {}", s);
        return s;
    }

    public void validateUserCode(String username, String code) {

        log.debug("validateUserCode() received: {}, {}", username, code);

        UserEntity userEntity = userRepository.findByUsername(username);

        if (Objects.equals(userEntity.getCode(), code)) {
            userRepository.save(userEntity.setActivated("yes"));
            log.info("User with username: {}, has been validated", username);
        } else {
            log.error("User code entered: {}, does not match", code);
            throw new UserCodeDoesNotMatchException();
        }
    }

    public void resendCode(String username) {

        log.debug("resendCode() received: {}", username);

        UserEntity userEntity = userRepository.findByUsername(username);

        String receiver = userEntity.getEmail();
        String code = userEntity.getCode();

        sendEmail(receiver, code);
    }

    public void generateNewCode(String username) {

        log.debug("generateNewCode() received: {}", username);

        UserEntity userEntity = userRepository.findByUsername(username);

        String newCode = generateVerificationCode();
        log.info("User with username: {}, has generated a new code: {}", username, newCode);
        userRepository.save(userEntity.setCode(newCode));

        resendCode(username);
    }

    public void isUsernameActivated(String username) {

        log.debug("isUsernameActivated() received: {}", username);

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            log.error("Failed to check if username: {}, is activated as the account does not exist", username);
            throw new UserDoesNotExistException();
        } else if (userEntity.getActivated() == null) {
            log.error("Failed as account with username: {}, has not been activated", username);
            throw new UserNotActivatedException();
        }

        generateNewCode(username);
    }

    public void resetPassword(String username, String newPassword, String code) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        log.debug("resetPassword() received: {}, {}, {}", username, newPassword, code);

        UserEntity userEntity = userRepository.findByUsername(username);

        String generatedSecuredPasswordHash = generatePasswordHash(newPassword);

        if (userEntity.getCode().equals(code)) {
            userRepository.save(userEntity.setPassword(generatedSecuredPasswordHash));
            log.info("User with username: {}, has reset their password", username);
        } else {
            log.error("User code entered: {}, does not match", code);
            throw new UserCodeDoesNotMatchException();
        }
    }

    public boolean validateLogin(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        log.debug("validateLogin() received: {}, {}", username, password);

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity != null) {
            if (userEntity.getActivated() != null) {
                return validatePassword(password, userEntity.getPassword());
            } else {
                log.error("Failed as account with username: {}, has not been activated", username);
                throw new UserNotActivatedException();
            }
        } else {
            log.error("Failed to check if username: {}, is activated as the account does not exist", username);
            throw new UserDoesNotExistException();
        }
    }

    public boolean validatePassword(String enteredPassword, String storedPassword) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        log.debug("validatePassword() received: {}, {}", enteredPassword, storedPassword);

        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(enteredPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = factory.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) {

        log.debug("fromHex() received: {}", hex);

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        log.debug("fromHex() returned: {}", bytes);
        return bytes;
    }

    public List<MunroBagDto> getMunrosBaggedByUsername(String username) {

        log.debug("getMunrosBaggedByUsername() received: {}", username);

        List<MunroBagEntity> munroBagEntities = StreamSupport.stream(munroBagRepository.findMunroBagByUsername(username).spliterator(), false)
                .collect(Collectors.toList());

        List<MunroBagDto> munroBagDtos = munroBagEntities.stream()
                .map(entity -> MunroBagDto.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .mountainId(entity.getMountainId())
                        .mountainName(entity.getMountainName())
                        .date(entity.getDate())
                        .rating(entity.getRating())
                        .build()
                )
                .collect(Collectors.toList());

        log.debug("getMunrosBaggedByUsername() returned: {}", munroBagDtos);
        return munroBagDtos;
    }

    public List<MunroBagDto> addMunroToBag(MunroBagDto munroBagDto) {

        log.debug("addMunroToBag() received: {}", munroBagDto);

        List<MunroBagDto> usersBag = getMunrosBaggedByUsername(munroBagDto.getUsername());

        for (MunroBagDto m : usersBag) {
            if (m.getMountainId() == munroBagDto.getMountainId()) {
                log.error("Failed to add Munro as: {}, has already been added to users: {}, account", munroBagDto.getMountainName(), munroBagDto.getUsername());
                throw new DuplicateEntryException();
            }
        }

        MunroBagEntity munroBagEntity = new MunroBagEntity();

        munroBagEntity.setUsername(munroBagDto.getUsername())
                .setMountainId(munroBagDto.getMountainId())
                .setMountainName(munroBagDto.getMountainName())
                .setDate(munroBagDto.getDate())
                .setRating(munroBagDto.getRating());

        munroBagRepository.save(munroBagEntity);
        log.info("User : {}, added: {}, to their bag", munroBagEntity.getUsername(), munroBagEntity.getMountainName());

        return getMunrosBaggedByUsername(munroBagEntity.getUsername());
    }

    public List<MunroBagDto> removeMunroFromBag(MunroBagDto munroBagDto) {

        log.debug("removeMunroFromBag() received: {}", munroBagDto);

        MunroBagEntity munroBagEntity = new MunroBagEntity();

        munroBagEntity.setId(munroBagDto.getId())
                .setUsername(munroBagDto.getUsername())
                .setMountainId(munroBagDto.getMountainId())
                .setMountainName(munroBagDto.getMountainName())
                .setDate(munroBagDto.getDate())
                .setRating(munroBagDto.getRating());

        munroBagRepository.delete(munroBagEntity);
        log.info("User : {}, deleted: {}, from their bag", munroBagEntity.getUsername(), munroBagEntity.getMountainName());

        return getMunrosBaggedByUsername(munroBagEntity.getUsername());
    }

    public List<TripDto> getAllTrips() {

        List<TripEntity> tripEntities = StreamSupport.stream(tripRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        List<TripDto> tripDtos = tripEntities.stream()
                .map(entity -> TripDto.builder()
                        .id(entity.getId())
                        .usernames(new ArrayList<>())
                        .mountainId(entity.getMountainId())
                        .mountainName(entity.getMountainName())
                        .date(entity.getDate())
                        .build()).collect(Collectors.toList());

        tripDtos.forEach(tripDto -> {
            List<TripGroupEntity> tripGroupEntities = tripGroupRepository.findUsernamesByTripId(tripDto.getId());

            tripGroupEntities.forEach(tripGroup -> {
                tripDto.getUsernames().add(tripGroup.getUsername());
            });

            TripEntity tripEntity = new TripEntity();

            tripEntity.setId(tripDto.getId())
                    .setMountainId(tripDto.getMountainId())
                    .setMountainName(tripDto.getMountainName())
                    .setDate(tripDto.getDate());

            if (tripDto.getUsernames().isEmpty()) {
                tripRepository.delete(tripEntity);
                log.info("Trip: {}, has been deleted as it did not contain any users", tripEntity.getId());
            }
        });

        log.debug("getAllTrips() returned: {}", tripDtos);
        return tripDtos;
    }

    public List<TripDto> addUserToTrip(String username, TripDto tripDto) {

        log.debug("addUserToTrip() received: {}, {}", username, tripDto);

        tripGroupRepository.findAll().forEach(tripGroupEntity -> {
            if ((tripGroupEntity.getTripId() == tripDto.getId()) &&
                    (Objects.equals(tripGroupEntity.getUsername(), username))) {
                log.error("Failed to add user: {}, to the trip as they are already on the trip", username);
                throw new DuplicateEntryException();
            }
        });

        TripGroupEntity tripGroupEntity = new TripGroupEntity();

        tripGroupEntity.setTripId(tripDto.getId())
                .setUsername(username);

        tripGroupRepository.save(tripGroupEntity);
        log.info("User: {}, has been added to trip: {}", username, tripGroupEntity.getId());

        return getAllTrips();
    }

    public List<TripDto> removeUserFromTrip(String username, TripDto tripDto) {

        log.debug("removeUserFromTrip() received: {}, {}", username, tripDto);

        TripGroupEntity tripGroupEntity = new TripGroupEntity();

        TripGroupDto tripGroupDto = getIdByUsernameAndTripId(username, tripDto.getId());

        tripGroupEntity.setId(tripGroupDto.getId())
                .setTripId(tripDto.getId())
                .setUsername(username);

        tripGroupRepository.delete(tripGroupEntity);
        log.info("User: {}, has been removed from trip: {}", username, tripGroupEntity.getId());

        return getAllTrips();
    }

    public TripGroupDto getIdByUsernameAndTripId(String username, long tripId) {

        log.debug("getIdByUsernameAndTripId() received: {}, {}", username, tripId);

        TripGroupEntity tripGroupEntity = tripGroupRepository.findIdByUsernameAndTripId(username, tripId);

        TripGroupDto tripGroupDto = TripGroupDto.builder()
                .id(tripGroupEntity.getId())
                .tripId(tripGroupEntity.getTripId())
                .username(tripGroupEntity.getUsername())
                .build();

        log.debug("getIdByUsernameAndTripId() returned: {}", tripGroupDto);
        return tripGroupDto;
    }

    public List<TripDto> getTripsByMountainName(String mountainName) {

        log.debug("getTripsByMountainName() received: {}", mountainName);

        List<TripEntity> tripEntities = StreamSupport.stream(tripRepository.findTripsByMountainName(mountainName)
                .spliterator(), false).collect(Collectors.toList());

        List<TripDto> tripDtos = tripEntities.stream()
                .map(entity -> TripDto.builder()
                        .id(entity.getId())
                        .usernames(new ArrayList<>())
                        .mountainId(entity.getMountainId())
                        .mountainName(entity.getMountainName())
                        .date(entity.getDate())
                        .build()).collect(Collectors.toList());

        tripDtos.forEach(tripDto -> {
            List<TripGroupEntity> tripGroupEntities = tripGroupRepository.findUsernamesByTripId(tripDto.getId());
            tripGroupEntities.forEach(tripGroup -> {
                tripDto.getUsernames().add(tripGroup.getUsername());
            });
        });

        log.debug("getTripsByMountainName() returned: {}", tripDtos);
        return tripDtos;
    }

    public List<TripDto> getTripsByDate(String date) {

        log.debug("getTripsByDate() received: {}", date);

        List<TripEntity> tripEntities = StreamSupport.stream(tripRepository.findTripsByDate(date)
                .spliterator(), false).collect(Collectors.toList());

        List<TripDto> tripDtos = tripEntities.stream()
                .map(entity -> TripDto.builder()
                        .id(entity.getId())
                        .usernames(new ArrayList<>())
                        .mountainId(entity.getMountainId())
                        .mountainName(entity.getMountainName())
                        .date(entity.getDate())
                        .build()).collect(Collectors.toList());

        tripDtos.forEach(tripDto -> {
            List<TripGroupEntity> tripGroupEntities = tripGroupRepository.findUsernamesByTripId(tripDto.getId());
            tripGroupEntities.forEach(tripGroup -> {
                tripDto.getUsernames().add(tripGroup.getUsername());
            });
        });

        log.debug("getTripsByDate() returned: {}", tripDtos);
        return tripDtos;
    }

    public List<TripDto> createTrip(String username, TripDto tripDto) {

        log.debug("createTrip() received: {}, {}", username, tripDto);

        tripRepository.findAll().forEach(tripEntity -> {
            if ((Objects.equals(tripEntity.getMountainName(), tripDto.getMountainName())) &&
                    ((Objects.equals(tripEntity.getDate(), tripDto.getDate())))) {
                log.error("Failed to create trip as a trip climbing: {}, on date: {}, already exists", tripDto.getMountainName(), tripDto.getDate());
                throw new DuplicateEntryException();
            }
        });

        TripEntity tripEntity = new TripEntity();

        tripEntity.setMountainId(tripDto.getMountainId())
                .setMountainName(tripDto.getMountainName())
                .setDate(tripDto.getDate());

        tripRepository.save(tripEntity);
        log.info("Trip: {}, has been added to the trip table", tripEntity.getId());

        TripGroupEntity tripGroupEntity = new TripGroupEntity();

        tripGroupEntity.setTripId(tripEntity.getId())
                .setUsername(username);

        tripGroupRepository.save(tripGroupEntity);
        log.info("TripGroup: {}, has been added to the trip_group table", tripGroupEntity.getId());

        return getAllTrips();
    }

    public List<ReviewDto> getAllReviews() {

        List<ReviewEntity> reviewEntities = StreamSupport.stream(reviewRepository.findAll().spliterator(),
                false).collect(Collectors.toList());

        List<ReviewDto> reviewDtos = reviewEntities.stream()
                .map(entity -> ReviewDto.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .reviewDate(entity.getReviewDate())
                        .mountainId(entity.getMountainId())
                        .mountainName(entity.getMountainName())
                        .walkDate(entity.getWalkDate())
                        .rating(entity.getRating())
                        .comment(entity.getComment())
                        .build()
                )
                .collect(Collectors.toList());

        log.debug("getAllReviews() returned: {}", reviewDtos);
        return reviewDtos;
    }

    public List<ReviewDto> createReview(ReviewDto reviewDto) {

        log.debug("createReview() received: {}", reviewDto);

        ReviewEntity reviewEntity = new ReviewEntity();

        reviewEntity.setUsername(reviewDto.getUsername())
                .setReviewDate(reviewDto.getReviewDate())
                .setMountainId(reviewDto.getMountainId())
                .setMountainName(reviewDto.getMountainName())
                .setWalkDate(reviewDto.getWalkDate())
                .setRating(reviewDto.getRating())
                .setComment(reviewDto.getComment());

        reviewRepository.save(reviewEntity);
        log.info("Review: {}, has been added to the review table", reviewEntity.getId());

        return getAllReviews();
    }

    public List<ReviewDto> getReviewsByMountainName(String name) {

        log.debug("getReviewsByMountainName() received: {}", name);

        List<ReviewEntity> reviewEntities = StreamSupport.stream(reviewRepository.findReviewsByMountainName(name).spliterator(),
                false).collect(Collectors.toList());

        List<ReviewDto> reviewDtos = reviewEntities.stream()
                .map(entity -> ReviewDto.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .reviewDate(entity.getReviewDate())
                        .mountainId(entity.getMountainId())
                        .mountainName(entity.getMountainName())
                        .walkDate(entity.getWalkDate())
                        .rating(entity.getRating())
                        .comment(entity.getComment())
                        .build()
                )
                .collect(Collectors.toList());

        log.debug("getReviewsByMountainName() returned: {}", reviewDtos);
        return reviewDtos;
    }
}
