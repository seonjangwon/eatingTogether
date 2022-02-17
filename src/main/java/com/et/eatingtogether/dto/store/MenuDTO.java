package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.MenuEntity;
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

    private Long menuNumber;
    private Long storeNumber;
    private Long storeCategoryNumber;
    private String storeCategoryName;

    private String menuName;
    private MultipartFile menuFile;
    private String menuFilename;
    private int menuPrice;
    private String menuExplain;



    public static MenuDTO toMenuDetailDTO (MenuEntity menuEntity)   {
        MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenuNumber(menuEntity.getMenuNumber());
            menuDTO.setMenuName(menuEntity.getMenuName());
            menuDTO.setMenuExplain(menuEntity.getMenuExplain());
            menuDTO.setMenuPrice(menuEntity.getMenuPrice());
            menuDTO.setMenuFilename(menuEntity.getMenuFilename());
            menuDTO.setStoreNumber(menuEntity.getStoreEntity().getStoreNumber());
            menuDTO.setStoreCategoryName(menuEntity.getStoreCategoryEntity().getStoreCategoryName());
        return menuDTO;
    }
}
