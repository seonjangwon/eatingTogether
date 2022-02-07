package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity,Long> {
}
