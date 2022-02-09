package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;

import java.io.IOException;
import java.util.List;

public interface StoreService {
    boolean login(StoreLoginDTO storeLoginDTO);

    Long save(StoreSaveDTO storeSaveDTO) throws IOException;

    List<StoreDetailDTO> findAll();

    StoreDetailDTO findById(Long categoryNum);
}
