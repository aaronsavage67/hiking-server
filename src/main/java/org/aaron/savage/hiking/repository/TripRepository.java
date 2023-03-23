package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.TripEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<TripEntity, Long> {

    List<TripEntity> findTripsByMountainName(String mountainName);

    List<TripEntity> findTripsByDate(String date);
}