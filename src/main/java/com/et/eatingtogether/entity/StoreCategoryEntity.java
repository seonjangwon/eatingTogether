package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.store.StoreCategoryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "storeCategory_table")
public class StoreCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeCategory_number")
    private Long storeCategoryNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;

    private String storeCategoryName;

    //지원
    public static StoreCategoryEntity addSmallCategory (StoreCategoryDTO storeCategoryDTO, StoreEntity storeEntity) {
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity);
        storeCategoryEntity.setStoreCategoryName(storeCategoryDTO.getStoreCategoryName());
        return storeCategoryEntity;
    }
}
