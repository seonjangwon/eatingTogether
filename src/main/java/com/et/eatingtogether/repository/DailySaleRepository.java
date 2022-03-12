package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.DailySaleEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailySaleRepository extends JpaRepository<DailySaleEntity,Long> {
    List<DailySaleEntity> findByStoreEntity(StoreEntity storeEntity);
    Optional<DailySaleEntity> findByDailySaleTimeAndStoreEntity(LocalDate localDate,StoreEntity storeEntity);
}
