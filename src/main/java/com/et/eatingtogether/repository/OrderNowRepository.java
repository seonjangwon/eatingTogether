package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.OrderNowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderNowRepository extends JpaRepository<OrderNowEntity,Long> {
}
