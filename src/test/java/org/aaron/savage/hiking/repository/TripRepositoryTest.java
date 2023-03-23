package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.TripEntity;
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
class TripRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TripRepository tripRepository;

    @BeforeEach
    public void clearDB() {
        tripRepository.findAll().forEach(entry -> testEntityManager.remove(entry));
    }

    private TripEntity createTrip() {

        return new TripEntity()
                .setMountainId(14)
                .setMountainName("Ben Nevis")
                .setDate("27/05/2000");
    }

    @Test
    public void testThatEntryCanBeRetrieved() {

        //arrange
        TripEntity expectedTrip = createTrip();
        testEntityManager.persist(expectedTrip);

        //act
        List<TripEntity> actualTrips = StreamSupport.stream(tripRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        //assert
        assertThat(actualTrips).containsOnly(expectedTrip);
    }

    @Test
    public void testThatEntryCanBeRetrievedByMountainName() {

        //arrange
        TripEntity expectedTrip = createTrip();
        testEntityManager.persist(expectedTrip);

        //act
        List<TripEntity> actualTrips = tripRepository.findTripsByMountainName("Ben Nevis");

        //assert
        assertThat(actualTrips).containsOnly(expectedTrip);
    }

    @Test
    public void testThatEntryCanBeRetrievedByDate() {

        //arrange
        TripEntity expectedTrip = createTrip();
        testEntityManager.persist(expectedTrip);

        //act
        List<TripEntity> actualTrips = tripRepository.findTripsByDate("27/05/2000");

        //assert
        assertThat(actualTrips).containsOnly(expectedTrip);
    }
}