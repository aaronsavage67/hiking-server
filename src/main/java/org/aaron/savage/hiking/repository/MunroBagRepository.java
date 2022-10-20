package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.MunroBagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunroBagRepository extends CrudRepository<MunroBagEntity, Long> {

    List<MunroBagEntity> findByUsername(String username);
}
