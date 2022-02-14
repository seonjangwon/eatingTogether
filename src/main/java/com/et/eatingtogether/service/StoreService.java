package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

public interface StoreService {
    boolean login(StoreLoginDTO storeLoginDTO);

    Long save(StoreSaveDTO storeSaveDTO) throws IOException;

    List<StoreDetailDTO> findAll();

    StoreDetailDTO findById(String storeName);

    void saveMenu(MenuDTO menuDTO) throws IOException;

}
