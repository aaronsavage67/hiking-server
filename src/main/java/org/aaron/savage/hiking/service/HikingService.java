package org.aaron.savage.hiking.service;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.entity.*;
import org.aaron.savage.hiking.exception.DuplicateEntryException;
import org.aaron.savage.hiking.exception.FailedToSendEmailException;
import org.aaron.savage.hiking.exception.UserCodeDoesNotMatchException;
import org.aaron.savage.hiking.exception.UserNotActivated;
import org.aaron.savage.hiking.repository.*;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class HikingService {

    MountainRepository mountainRepository;

    UserRepository userRepository;

    MunroBagRepository munroBagRepository;

    TripRepository tripRepository;

    TripGroupRepository tripGroupRepository;

    JavaMailSender emailSender;

    static final String EMAIL_FROM = "noreply@gmail.com";
    static final String EMAIL_SUBJECT = "Verification Code";
    static final String EMAIL_TEXT = "Hello from Hiking App! Your verification code is: ";

    public List<MountainDto> getAllMountains() {

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return mountainEntities.stream()
                .map(entity -> MountainDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .height(entity.getHeight())
                        .coords(entity.getCoords())
                        .region(entity.getRegion())
                        .routeImage(entity.getRouteImage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<MountainDto> getMountainByName(String name) {

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findByName(name).spliterator(), false)
                .collect(Collectors.toList());

        return mountainEntities.stream()
                .map(entity -> MountainDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .height(entity.getHeight())
                        .coords(entity.getCoords())
                        .region(entity.getRegion())
                        .routeImage(entity.getRouteImage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public MountainDto getMountainById(long id) {

        MountainEntity mountainEntity = mountainRepository.findById(id);

        return MountainDto.builder()
                .id(mountainEntity.getId())
                .name(mountainEntity.getName())
                .description(mountainEntity.getDescription())
                .height(mountainEntity.getHeight())
                .coords(mountainEntity.getCoords())
                .region(mountainEntity.getRegion())
                .routeImage(mountainEntity.getRouteImage())
                .build();
    }

    public List<MountainDto> getMountainsByRegion(String region) {

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findByRegion(region)
                .spliterator(), false).collect(Collectors.toList());

        return mountainEntities.stream()
                .map(entity -> MountainDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .height(entity.getHeight())
                        .coords(entity.getCoords())
                        .region(entity.getRegion())
                        .routeImage(entity.getRouteImage())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public UserDto getUserByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .activated(userEntity.getActivated())
                .build();
    }

    public UserDto getUserByPassword(String password) {

        UserEntity userEntity = userRepository.findByPassword(password);

        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .activated(userEntity.getActivated())
                .build();
    }

    private String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = generateSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) {

        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public void createUser(String username, String password, String email) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        UserEntity userEntity = new UserEntity();

        String generatedSecuredPasswordHash = generatePasswordHash(password);

        String code = generateVerificationCode();

        userEntity.setUsername(username).setPassword(generatedSecuredPasswordHash).setEmail(email).setCode(code);

        try {
            userRepository.save(userEntity);
            sendEmail(email, code);
        } catch (MailException m) {
            userRepository.delete(userEntity);
            throw new FailedToSendEmailException();
        } catch (DataAccessException e) {
            throw new DuplicateEntryException();
        }
    }

    private void sendEmail(String receiver, String code) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_FROM);
        message.setTo(receiver);
        message.setSubject(EMAIL_SUBJECT);
        message.setText(EMAIL_TEXT + code);
        emailSender.send(message);
    }

    private String generateVerificationCode() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }

    public void validateUserCode(String username, String code) {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (Objects.equals(userEntity.getCode(), code)) {
            userRepository.save(userEntity.setActivated("yes"));
        } else {
            throw new UserCodeDoesNotMatchException();
        }
    }

    public void resendCode(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        String receiver = userEntity.getEmail();
        String code = userEntity.getCode();

        sendEmail(receiver, code);
    }

    public void generateNewCode(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        String newCode = generateVerificationCode();
        userRepository.save(userEntity.setCode(newCode));

        resendCode(username);
    }

    public boolean isUsernameActivated(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (userEntity.getActivated() == null) {
            throw new UserNotActivated();
        }

        generateNewCode(username);
        return true;
    }

    public void resetPassword(String username, String newPassword, String code) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

        UserEntity userEntity = userRepository.findByUsername(username);

        String generatedSecuredPasswordHash = generatePasswordHash(newPassword);

        if (userEntity.getCode().equals(code)) {
            userRepository.save(userEntity.setPassword(generatedSecuredPasswordHash));
        } else {
            throw new UserCodeDoesNotMatchException();
        }
    }

    public boolean validateLogin(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (validatePassword(password, userEntity.getPassword())) {
            if (userEntity.getActivated() == null) {
                throw new UserNotActivated();
            }
            return true;
        }
        return false;
    }

    private boolean validatePassword(String enteredPassword, String storedPassword) throws NoSuchAlgorithmException,
            InvalidKeySpecException {

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

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public List<MunroBagDto> getMunrosBaggedByUsername(String username) {

        List<MunroBagEntity> munroBagEntities = StreamSupport.stream(munroBagRepository.findMunroBagByUsername(username).spliterator(), false)
                .collect(Collectors.toList());

        return munroBagEntities.stream()
                .map(entity -> MunroBagDto.builder()
                        .id(entity.getId())
                        .username(entity.getUsername())
                        .mountainId(entity.getMountainId())
                        .date(entity.getDate())
                        .rating(entity.getRating())
                        .comments(entity.getComments())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public TripDto getTripByOrganiserId(long organiserId) {

        TripEntity tripEntity = tripRepository.findByOrganiserId(organiserId);

        return TripDto.builder()
                .id(tripEntity.getId())
                .organiserId(tripEntity.getOrganiserId())
                .mountainId(tripEntity.getMountainId())
                .date(tripEntity.getDate())
                .description(tripEntity.getDescription())
                .build();
    }

    public TripGroupDto getTripGroupByTripId(long tripId) {

        TripGroupEntity tripGroupEntity = tripGroupRepository.findByTripId(tripId);

        return TripGroupDto.builder()
                .id(tripGroupEntity.getId())
                .tripId(tripGroupEntity.getTripId())
                .username(tripGroupEntity.getUsername())
                .status(tripGroupEntity.getStatus())
                .build();
    }
}
