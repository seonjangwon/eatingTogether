package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.StoreBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreBlacklistRepository extends JpaRepository<StoreBlacklistEntity,Long> {
}
