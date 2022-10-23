package org.aaron.savage.hiking.service;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.*;
import org.aaron.savage.hiking.entity.*;
import org.aaron.savage.hiking.repository.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class HikingService {

    MountainRepository mountainRepository;

    UserRepository userRepository;

    MunroBagRepository munroBagRepository;

    TripRepository tripRepository;

    TripGroupRepository tripGroupRepository;

    public List<MountainDto> getMountains(String name) {

        List<MountainEntity> mountainEntities = StreamSupport.stream(mountainRepository.findByName(name).spliterator(), false)
                .collect(Collectors.toList());

        return mountainEntities.stream()
                .map(entity -> MountainDto.builder()
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

    public UserDto getUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username);

        return UserDto.builder()
                .name(userEntity.getName())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    public List<MunroBagDto> getMunrosBaggedByUsername(String username) {

        List<MunroBagEntity> munroBagEntities = StreamSupport.stream(munroBagRepository.findByUsername(username).spliterator(), false)
                .collect(Collectors.toList());

        return munroBagEntities.stream()
                .map(entity -> MunroBagDto.builder()
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
                .organiserId(tripEntity.getOrganiserId())
                .mountainId(tripEntity.getMountainId())
                .date(tripEntity.getDate())
                .description(tripEntity.getDescription())
                .build();
    }

    public TripGroupDto getTripGroupByTripId(long tripId) {

        TripGroupEntity tripGroupEntity = tripGroupRepository.findByTripId(tripId);

        return TripGroupDto.builder()
                .tripId(tripGroupEntity.getTripId())
                .username(tripGroupEntity.getUsername())
                .status(tripGroupEntity.getStatus())
                .build();
    }
}
