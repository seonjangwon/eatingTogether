package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.OrderMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenuEntity,Long> {
}
