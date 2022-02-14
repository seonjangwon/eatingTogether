package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategoryDTO {

    private StoreEntity storeEntity;
    private String storeCategoryName;

}
