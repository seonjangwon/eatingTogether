package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository sr;


    @Override
    public boolean login(StoreLoginDTO storeLoginDTO) {
        System.out.println("StoreSerivceImpl.login");
        StoreEntity storeEntity = sr.findByStoreEmail(storeLoginDTO.getStoreEmail());
        if (storeEntity != null)    {
            if(storeEntity.getStorePassword().equals(storeLoginDTO.getStorePassword())) {
            return true;
        }   else    {
            return false;
        }}  else    {
            return false;
        }
    }
}
