package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    Optional<CustomerEntity> findByCustomerEmail(String customerEmail);
}
