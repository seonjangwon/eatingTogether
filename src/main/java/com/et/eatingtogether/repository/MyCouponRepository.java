package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.CouponEntity;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.MyCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyCouponRepository extends JpaRepository<MyCouponEntity,Long> {
    Optional<MyCouponEntity> findByCustomerEntityAndCouponEntity(CustomerEntity customerEntity, CouponEntity couponEntity);
}
