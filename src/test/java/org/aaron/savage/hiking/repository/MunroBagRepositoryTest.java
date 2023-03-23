package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.MunroBagEntity;
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
class MunroBagRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MunroBagRepository munroBagRepository;

    @BeforeEach
    public void clearDB() {
        munroBagRepository.findAll().forEach(entry -> testEntityManager.remove(entry));
    }

    private MunroBagEntity createMunroBag() {

        return new MunroBagEntity()
                .setUsername("user67")
                .setMountainId(110L)
                .setMountainName("Am Bodach")
                .setDate("27/05/2000")
                .setRating("4");
    }

    @Test
    public void testThatEntryCanBeRetrieved() {

        //arrange
        MunroBagEntity expectedMunroBag = createMunroBag();
        testEntityManager.persist(expectedMunroBag);

        //act
        List<MunroBagEntity> actualMunroBag = StreamSupport.stream(munroBagRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMunroBag).containsOnly(expectedMunroBag);
    }

    @Test
    public void testThatEntryCanBeRetrievedByName() {

        //arrange
        MunroBagEntity expectedMunroBag = createMunroBag();
        testEntityManager.persist(expectedMunroBag);

        //act
        List<MunroBagEntity> actualMunroBags = StreamSupport.stream(munroBagRepository.findMunroBagByUsername("user67").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMunroBags).containsOnly(expectedMunroBag);
    }

    @Test
    public void testThatDuplicateEntryCanBeRetrievedByName() {

        //arrange
        MunroBagEntity expectedMunroBag1 = createMunroBag();
        MunroBagEntity expectedMunroBag2 = createMunroBag();
        testEntityManager.persist(expectedMunroBag1);
        testEntityManager.persist(expectedMunroBag2);

        //act
        List<MunroBagEntity> actualMunroBags = StreamSupport.stream(munroBagRepository.findMunroBagByUsername("user67").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMunroBags).containsOnly(expectedMunroBag1, expectedMunroBag2);
    }

    @Test
    public void testThatDuplicateEntryDoesNotExistWhenRetrievedByName() {

        //arrange

        //act
        List<MunroBagEntity> actualMunroBags = StreamSupport.stream(munroBagRepository.findMunroBagByUsername("user67").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualMunroBags).isEmpty();
    }
}