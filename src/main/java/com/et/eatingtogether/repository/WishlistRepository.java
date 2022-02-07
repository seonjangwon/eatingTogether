package com.et.eatingtogether.repository;

import com.et.eatingtogether.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<WishlistEntity,Long> {
}
