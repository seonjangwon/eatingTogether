package com.et.eatingtogether.repository;


import com.et.eatingtogether.entity.CustomerReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReportRepository extends JpaRepository<CustomerReportEntity, Long> {
}
