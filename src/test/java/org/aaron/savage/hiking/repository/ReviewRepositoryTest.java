package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.ReviewEntity;
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
class ReviewRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void clearDB() {
        reviewRepository.findAll().forEach(entry -> testEntityManager.remove(entry));
    }

    private ReviewEntity createReview() {

        return new ReviewEntity()
                .setUsername("aaron67")
                .setReviewDate("10/3/2023")
                .setMountainId(10L)
                .setMountainName("Ben Nevis")
                .setWalkDate("17/12/2019")
                .setRating("1")
                .setComment("everyone had fun");
    }

    @Test
    public void testThatEntryCanBeRetrieved() {

        //arrange
        ReviewEntity expectedReview = createReview();
        testEntityManager.persist(expectedReview);

        //act
        List<ReviewEntity> actualReviews = StreamSupport.stream(reviewRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualReviews).containsOnly(expectedReview);
    }

    @Test
    public void testThatEntryCanBeRetrievedByName() {

        //arrange
        ReviewEntity expectedReview = createReview();
        testEntityManager.persist(expectedReview);

        //act
        List<ReviewEntity> actualReviews = StreamSupport.stream(reviewRepository.findReviewsByMountainName("Ben Nevis").spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualReviews).containsOnly(expectedReview);
    }
}