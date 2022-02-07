package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.MyCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCouponRepository extends JpaRepository<MyCouponEntity,Long> {
}
