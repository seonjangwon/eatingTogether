package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<BasketEntity,Long> {
}
