package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.MenuEntity;
import com.et.eatingtogether.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity,Long> {
    List<MenuEntity> findAll();


    List<MenuEntity> findByStoreEntity(StoreEntity storeEntity);

}
