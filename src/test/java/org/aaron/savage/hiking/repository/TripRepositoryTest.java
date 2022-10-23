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
                .setOrganiserId(910L)
                .setMountainId(14L)
                .setDate("27/05/2000")
                .setDescription("Walk is taking place now");
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
    public void testThatEntryCanBeRetrievedByOrganiserId() {

        //arrange
        TripEntity expectedTrip = createTrip();
        testEntityManager.persist(expectedTrip);

        //act
        TripEntity actualTrips = tripRepository.findByOrganiserId(910L);

        //assert
        assertThat(actualTrips).isEqualTo(expectedTrip);
    }

    @Test
    public void testThatDuplicateEntryDoesNotExistWhenRetrievedByOrganiserId() {

        //arrange

        //act
        TripEntity actualTrips = tripRepository.findByOrganiserId(910L);

        //assert
        assertThat(actualTrips).isNull();
    }
}