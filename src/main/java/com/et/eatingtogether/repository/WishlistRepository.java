package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistEntity,Long> {
    Optional<WishlistEntity> findByCustomerEntityAndStoreEntity(CustomerEntity customerEntity, StoreEntity storeEntity);
}
