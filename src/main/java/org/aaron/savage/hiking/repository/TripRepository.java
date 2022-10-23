package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.TripEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends CrudRepository<TripEntity, Long> {

    TripEntity findByOrganiserId(long organiserId);
}