package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.TripGroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripGroupRepository extends CrudRepository<TripGroupEntity, Long> {

    List<TripGroupEntity> findUsernamesByTripId(long tripId);

    TripGroupEntity findIdByUsernameAndTripId(String username, long tripId);
}