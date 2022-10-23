package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.TripGroupEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TripGroupRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TripGroupRepository tripGroupRepository;

    @BeforeEach
    public void clearDB() {
        tripGroupRepository.findAll().forEach(entry -> testEntityManager.remove(entry));
    }

    private TripGroupEntity createTripGroup() {

        return new TripGroupEntity()
                .setUsername("user67")
                .setTripId(110L)
                .setStatus("Yes");
    }

    @Test
    public void testThatEntryCanBeRetrieved() {

        //arrange
        TripGroupEntity expectedTripGroup = createTripGroup();
        testEntityManager.persist(expectedTripGroup);

        //act
        List<TripGroupEntity> actualMountains = StreamSupport.stream(tripGroupRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMountains).containsOnly(expectedTripGroup);
    }

    @Test
    public void testThatEntryCanBeRetrievedByTripId() {

        //arrange
        TripGroupEntity expectedTripGroup = createTripGroup();
        testEntityManager.persist(expectedTripGroup);

        //act
        TripGroupEntity actualTripGroup = tripGroupRepository.findByTripId(110L);

        //assert
        assertThat(actualTripGroup).isEqualTo(expectedTripGroup);
    }

    @Test
    public void testThatDuplicateEntryDoesNotExistWhenRetrievedByTripId() {

        //arrange

        //act
        TripGroupEntity actualTripGroups = tripGroupRepository.findByTripId(110L);

        //assert
        assertThat(actualTripGroups).isNull();
    }
}