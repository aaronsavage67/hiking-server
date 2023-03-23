package org.aaron.savage.hiking.repository;

import org.aaron.savage.hiking.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, Long> {

    List<ReviewEntity> findReviewsByMountainName(String mountainName);
}
