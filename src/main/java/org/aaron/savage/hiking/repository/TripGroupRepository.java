package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.TripGroupEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripGroupRepository extends CrudRepository<TripGroupEntity, Long> {

    TripGroupEntity findByTripId(long tripId);
}