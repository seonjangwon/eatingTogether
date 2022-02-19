package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.BasketEntity;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<BasketEntity,Long> {

    Optional<BasketEntity> findByCustomerEntityAndAndMenuEntity(CustomerEntity customerEntity, MenuEntity menuEntity);
}
