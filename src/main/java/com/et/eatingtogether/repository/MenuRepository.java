package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuEntity,Long> {
}
