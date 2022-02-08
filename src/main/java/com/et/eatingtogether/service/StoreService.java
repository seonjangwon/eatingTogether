package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.StoreLoginDTO;

public interface StoreService {
    boolean login(StoreLoginDTO storeLoginDTO);
}
