package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.OrderEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    List<OrderEntity> findByStoreEntity(StoreEntity storeEntity);

}
