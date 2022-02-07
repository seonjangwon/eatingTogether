package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.CustomerBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerBlacklistRepository extends JpaRepository<CustomerBlacklistEntity,Long> {
}
