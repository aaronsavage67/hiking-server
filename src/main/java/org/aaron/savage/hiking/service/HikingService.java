package org.aaron.savage.hiking.service;

import lombok.AllArgsConstructor;
import org.aaron.savage.hiking.dto.MountainDto;
import org.aaron.savage.hiking.dto.MunroBagDto;
import org.aaron.savage.hiking.dto.UserDto;
import org.aaron.savage.hiking.entity.MountainEntity;
import org.aaron.savage.hiking.entity.MunroBagEntity;
import org.aaron.savage.hiking.entity.UserEntity;
import org.aaron.savage.hiking.repository.MountainRepository;
import org.aaron.savage.hiking.repository.MunroBagRepository;
import org.aaron.savage.hiking.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public class HikingService {

    MountainRepository mountainRepository;

    UserRepository userRepository;

    MunroBagRepository munroBagRepository;

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
}
