package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.StoreCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {

    private Long storeNumber;
    private Long storeCategoryNumber;
    private String storeCategoryName;

    private String menuName;
    private MultipartFile menuFile;
    private String menuFilename;
    private int menuPrice;
    private String menuExplain;

}
