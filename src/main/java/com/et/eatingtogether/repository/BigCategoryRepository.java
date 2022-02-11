package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.BigCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BigCategoryRepository extends JpaRepository<BigCategoryEntity,Long> {
    BigCategoryEntity findBybigCategoryNumber(Long bigCategoryNumber);
}
