package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.et.eatingtogether.dto.store.StoreDetailDTO.toStoreDetailDTO;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository sr;
    private final BigCategoryRepository bcr;


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

        /*        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO, memberEntity);
        * */
        BigCategoryEntity bigCategoryEntity = bcr.findBybigCategoryNumber(storeSaveDTO.getBigCategoryNumber());
        StoreEntity storeEntity = StoreEntity.toSaveStore(storeSaveDTO, bigCategoryEntity);
        return sr.save(storeEntity).getStoreNumber();
    }

    @Override
    public List<StoreDetailDTO> findAll() {

        List<StoreEntity> storeEntityList = sr.findAll();
        List<StoreDetailDTO> storeList = new ArrayList<>();
        for (StoreEntity se : storeEntityList)  {
            storeList.add(StoreDetailDTO.toStoreDetailDTO(se));
        }

        System.out.println("StoreServiceImpl.findAll");
        return storeList;
    }

    @Override
    public StoreDetailDTO findById(Long bigCategoryNumber) {
        Optional<StoreEntity> optionalStoreEntity = sr.findById(bigCategoryNumber);
        StoreDetailDTO storeDetailDTO = null;
        if (optionalStoreEntity.isPresent())    {
            StoreEntity storeEntity = optionalStoreEntity.get();
            storeDetailDTO = StoreDetailDTO.toStoreDetailDTO(storeEntity);
        }
        return storeDetailDTO;
    }
}
