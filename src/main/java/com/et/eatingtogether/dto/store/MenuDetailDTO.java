package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MenuDetailDTO {

        private Long menuNumber;
        private Long storeNumber;
        private Long storeCategoryNumber;
        private String storeCategoryName;

        private String menuName;
        private MultipartFile menuFile;
        private String menuFilename;
        private int menuPrice;
        private String menuExplain;


        public static MenuDetailDTO toDetailMenu (MenuEntity menuEntity){
            MenuDetailDTO menuDetailDTO = new MenuDetailDTO();
            menuDetailDTO.setMenuNumber(menuEntity.getMenuNumber());
            menuDetailDTO.setMenuName(menuEntity.getMenuName());
            menuDetailDTO.setStoreCategoryNumber(menuEntity.getStoreCategoryEntity().getStoreCategoryNumber());
            menuDetailDTO.setStoreCategoryName(menuEntity.getStoreCategoryEntity().getStoreCategoryName());
            menuDetailDTO.setMenuExplain(menuEntity.getMenuExplain());
            menuDetailDTO.setMenuPrice(menuEntity.getMenuPrice());
            menuDetailDTO.setMenuFilename(menuEntity.getMenuFilename());
            return menuDetailDTO;

        }
    }



