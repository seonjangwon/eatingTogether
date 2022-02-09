package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDetailDTO {

    private Long bigCategoryNumber;

    private String storeEmail;
    private String storePassword;

    private MultipartFile storeFile;
    private String storeFilename;
    private String storeName;
    private String storePhone;
    private String storeOpen;
    private String storeClose;
    private String storeAddress;

    //MemberEntity -> MemberDetailDTO

    public static StoreDetailDTO toStoreDetailDTO (StoreEntity storeEntity){
        StoreDetailDTO storeDetailDTO = new StoreDetailDTO();
        /*storeDetailDTO.setBigCategoryNumber(storeEntity.getBigCategoryEntity());*/
        storeDetailDTO.setStoreEmail(storeEntity.getStoreEmail());
        storeDetailDTO.setStorePassword(storeEntity.getStorePassword());
        storeDetailDTO.setStoreName(storeEntity.getStoreName());
        storeDetailDTO.setStoreFilename(storeEntity.getStoreFilename());
        storeDetailDTO.setStorePhone(storeEntity.getStorePhone());
        storeDetailDTO.setStoreOpen(storeEntity.getStoreOpen());
        storeDetailDTO.setStoreClose(storeEntity.getStoreClose());
        storeDetailDTO.setStoreAddress(storeEntity.getStoreAddress());
        return storeDetailDTO;
    }

}

