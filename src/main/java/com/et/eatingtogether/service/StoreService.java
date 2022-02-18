package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.entity.StoreCategoryEntity;

import java.io.IOException;
import java.util.List;

public interface StoreService {
    boolean login(StoreLoginDTO storeLoginDTO);

    Long save(StoreSaveDTO storeSaveDTO) throws IOException;

    List<StoreDetailDTO> findAll();
    List<BigCategoryDTO> findAllBc();
    
    StoreDetailDTO findById(String storeName);

    void saveMenu(MenuDTO menuDTO, StoreCategoryEntity storeCategoryEntity) throws IOException;

    List<StoreCategoryDTO> categoryList();

    StoreCategoryEntity categorySave(Long storeNumber, String storeCategoryName);

    StoreCategoryEntity findCategory(Long storeCategoryNumber);

    StoreDetailDTO findByNumber(Long storeNumber);

    List<MenuDTO> menuFindAll(Long storeNumber);

    List<StoreDetailDTO> findByBcNumber(Long bigCategoryNumber);

    MenuDetailDTO findByMenu(Long menuNumber);

    Long updateMenu(MenuDetailDTO menuDetailDTO) throws IOException;

    void deleteByMenu(Long menuNumber);

}
