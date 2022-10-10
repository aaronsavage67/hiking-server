package org.aaron.savage.hiking.service;

import org.aaron.savage.hiking.dto.MountainDto;
import org.aaron.savage.hiking.entity.MountainEntity;
import org.aaron.savage.hiking.repository.MountainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HikingServiceTest {

    private MountainRepository mountainRepository = mock(MountainRepository.class);

    private HikingService hikingService;

    @BeforeEach
    public void setUp() {
        hikingService = new HikingService(mountainRepository);
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
}