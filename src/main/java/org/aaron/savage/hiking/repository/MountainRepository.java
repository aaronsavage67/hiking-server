package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.MountainEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MountainRepository extends CrudRepository<MountainEntity, Long> {

    List<MountainEntity> findByName(String name);

    MountainEntity findByNameAndRegion(String name, String region);

    List<MountainEntity> findByRegion(String region);

    MountainEntity findById(long id);
}
