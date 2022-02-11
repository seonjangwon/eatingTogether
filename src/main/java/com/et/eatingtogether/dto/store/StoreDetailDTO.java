package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailDTO {
    private Long storeNumber;
    private Long bicCategoryNumber;
    private String storeEmail;
    private String storePassword;
    private String storeFilename;
    private String storeName;
    private String storePhone;
    private String storeOpen;
    private String storeClose;
    private String storeAddress;
    private int storeWish;

    public static StoreDetailDTO toStoreDetailDTO(StoreEntity storeEntity){
        StoreDetailDTO storeDetailDTO = new StoreDetailDTO();
        storeDetailDTO.setStoreNumber(storeEntity.getStoreNumber());
        storeDetailDTO.setStoreName(storeEntity.getStoreName());
        return storeDetailDTO;
    }

}
