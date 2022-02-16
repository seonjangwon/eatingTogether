package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.StoreCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryDTO {

    private Long storeCategoryNumber;
    private Long storeNumber;
    private String storeCategoryName;

    //
    public static StoreCategoryDTO toEntity(StoreCategoryEntity c) {
        StoreCategoryDTO storeCategoryDTO = new StoreCategoryDTO();
        storeCategoryDTO.setStoreCategoryNumber(c.getStoreCategoryNumber());
        storeCategoryDTO.setStoreNumber(c.getStoreCategoryNumber());
        storeCategoryDTO.setStoreCategoryName(c.getStoreCategoryName());
        return storeCategoryDTO;
    }
}
