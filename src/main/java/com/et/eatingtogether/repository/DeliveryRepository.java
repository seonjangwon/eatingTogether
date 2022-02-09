package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity,Long> {
    Optional<DeliveryEntity> findByIdAndDeliveryDname(Long Id, String deliveryDname);
}
