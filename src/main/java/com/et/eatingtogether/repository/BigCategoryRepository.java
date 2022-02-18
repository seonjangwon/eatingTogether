package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BigCategoryRepository extends JpaRepository<BigCategoryEntity,Long> {
    BigCategoryEntity findByBigCategoryNumber(Long bigCategoryNumber);

}
