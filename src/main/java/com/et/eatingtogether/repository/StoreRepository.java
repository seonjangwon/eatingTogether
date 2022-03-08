package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.MenuEntity;
import com.et.eatingtogether.entity.StoreCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity,Long> {
    StoreEntity findByStoreEmail(String storeEmail);

    List<StoreEntity> findByBigCategoryEntity(BigCategoryEntity bigCategoryEntity);

    List<StoreEntity> findByStoreNameContaining(String keyword);

}
