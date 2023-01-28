package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.MountainEntity;
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
class MountainRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MountainRepository mountainRepository;

    @BeforeEach
    public void clearDB() {
        mountainRepository.findAll().forEach(entry -> testEntityManager.remove(entry));
    }

    private MountainEntity createMountain() {

        return new MountainEntity()
                .setName("Ben Nevis")
                .setHeight("4411")
                .setDescription("the tallest Munro")
                .setRegion("Fort William")
                .setCoords("237824")
                .setRouteImage("image");
    }

    @Test
    public void testThatEntryCanBeRetrieved() {

        //arrange
        MountainEntity expectedMountain = createMountain();
        testEntityManager.persist(expectedMountain);

        //act
        List<MountainEntity> actualMountains = StreamSupport.stream(mountainRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMountains).containsOnly(expectedMountain);
    }

    @Test
    public void testThatEntryCanBeRetrievedByName() {

        //arrange
        MountainEntity expectedMountain = createMountain();
        testEntityManager.persist(expectedMountain);

        //act
        List<MountainEntity> actualMountains = StreamSupport.stream(mountainRepository.findByName("Ben Nevis").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMountains).containsOnly(expectedMountain);
    }

    @Test
    public void testThatDuplicateEntryCanBeRetrievedByName() {

        //arrange
        MountainEntity expectedMountain1 = createMountain();
        MountainEntity expectedMountain2 = createMountain();
        testEntityManager.persist(expectedMountain1);
        testEntityManager.persist(expectedMountain2);

        //act
        List<MountainEntity> actualMountains = StreamSupport.stream(mountainRepository.findByName("Ben Nevis").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMountains).containsOnly(expectedMountain1, expectedMountain2);
    }

    @Test
    public void testThatEntryCanBeRetrievedByRegion() {

        //arrange
        MountainEntity expectedMountain1 = createMountain().setRegion("Fort William");
        MountainEntity expectedMountain2 = createMountain().setRegion("Loch Lomond");
        testEntityManager.persist(expectedMountain1);
        testEntityManager.persist(expectedMountain2);

        //act
        List<MountainEntity> actualMountains = StreamSupport.stream(mountainRepository.findByRegion("Fort William").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMountains).containsOnly(expectedMountain1);
    }
    
    @Test
    public void testThatEntryCanBeRetrievedById() {

        //arrange
        MountainEntity expectedMountain1 = createMountain();
        MountainEntity expectedMountain2 = createMountain();
        expectedMountain1 = testEntityManager.persist(expectedMountain1);
        testEntityManager.persist(expectedMountain2);
        long expectedEntityId = expectedMountain1.getId();

        //act
        MountainEntity actualMountain = mountainRepository.findById(expectedEntityId);

        //assert
        assertThat(actualMountain).isEqualTo(expectedMountain1);
    }

    @Test
    public void testThatEntryCanBeRetrievedByNameAndRegion() {

        //arrange
        MountainEntity expectedMountain1 = createMountain().setRegion("Fort William");
        MountainEntity expectedMountain2 = createMountain().setRegion("Loch Lomond");
        testEntityManager.persist(expectedMountain1);
        testEntityManager.persist(expectedMountain2);

        //act
        MountainEntity actualMountain = mountainRepository.findByNameAndRegion("Ben Nevis", "Fort William");

        //assert
        assertThat(actualMountain).isEqualTo(expectedMountain1);
    }

    @Test
    public void testThatDuplicateEntryDoesNotExistWhenRetrievedByName() {

        //arrange

        //act
        List<MountainEntity> actualMountains = StreamSupport.stream(mountainRepository.findByName("Ben Nevis").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMountains).isEmpty();
    }

    @Test
    public void testThatEntryDoesNotExistWhenRetrievedByNameAndRegion() {

        //arrange

        //act
        MountainEntity actualMountain = mountainRepository.findByNameAndRegion("Ben Nevis", "Fort William");

        //assert
        assertThat(actualMountain).isNull();
    }
}