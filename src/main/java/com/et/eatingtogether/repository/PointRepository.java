package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity,Long> {
}
