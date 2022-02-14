package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity,Long> {
    StoreEntity findByStoreEmail(String storeEmail);

}
