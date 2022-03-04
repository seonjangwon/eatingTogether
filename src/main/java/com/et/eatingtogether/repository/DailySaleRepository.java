package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.DailySaleEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailySaleRepository extends JpaRepository<DailySaleEntity,Long> {
    List<DailySaleEntity> findByStoreEntity(StoreEntity storeEntity);
}
