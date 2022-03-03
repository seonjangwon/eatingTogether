package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.ReviewEntity;
import com.et.eatingtogether.entity.ReviewFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewFileRepository extends JpaRepository<ReviewFileEntity,Long> {
    List<ReviewFileEntity> findAllByReviewEntity(ReviewEntity reviewEntity);
}
