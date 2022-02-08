package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;

import java.io.IOException;

public interface StoreService {
    boolean login(StoreLoginDTO storeLoginDTO);

    Long save(StoreSaveDTO storeSaveDTO) throws IOException;
}
