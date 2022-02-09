package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    @Override
    public Long save(StoreSaveDTO storeSaveDTO) throws IOException {
        System.out.println("StoreSerivceImpl.storeSave");

        //가게사진 추가
        MultipartFile storeFile = storeSaveDTO.getStoreFile();
        String storeFilename = storeFile.getOriginalFilename();
        storeFilename = System.currentTimeMillis()+"-"+storeFilename;
        System.out.println("파일이름: "+storeFilename);
        String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\"+storeFilename;

        if (!storeFile.isEmpty()){
            storeFile.transferTo(new File(savePath));
        }
        storeSaveDTO.setStoreFilename(storeFilename);

        /*BigCategoryEntity bigCategoryEntity = bc.findByBigCategoryNumber();*/
        StoreEntity storeEntity = StoreEntity.toSaveStore(storeSaveDTO);
        return sr.save(storeEntity).getStoreNumber();
    }
}
