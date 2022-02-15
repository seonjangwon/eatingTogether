package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.MenuEntity;
import com.et.eatingtogether.entity.StoreCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.repository.MenuRepository;
import com.et.eatingtogether.repository.StoreCategoryRepository;
import com.et.eatingtogether.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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
    private final StoreCategoryRepository scr;
    private final MenuRepository mnr;
    private final HttpSession session;

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
        if (!storeSaveDTO.getStoreFile().isEmpty()) {
            MultipartFile storeFile = storeSaveDTO.getStoreFile();
            String storeFilename = storeFile.getOriginalFilename();
            storeFilename = System.currentTimeMillis()+"-"+storeFilename;
            System.out.println("파일이름: "+storeFilename);
            String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\"+storeFilename;
            if (!storeFile.isEmpty()){
                storeFile.transferTo(new File(savePath));
            }
            storeSaveDTO.setStoreFilename(storeFilename);
        }

        BigCategoryEntity bigCategoryEntity = bcr.findBybigCategoryNumber(storeSaveDTO.getBigCategoryNumber());
        StoreEntity storeEntity = StoreEntity.toSaveStore(storeSaveDTO, bigCategoryEntity);

        //0210 사용자가 입력한 이메일 중복체크
        StoreEntity emailCheckResult = sr.findByStoreEmail(storeSaveDTO.getStoreEmail());
        if (emailCheckResult != null)   { //64. 입력한 email이 null이 아닐 경우 65.예외 발생
            throw  new IllegalStateException("중복된 이메일입니다.");
            // 예외 종류: IllegalStateException, 예외 메세지: 중복된 이메일입니다.
        }

        return sr.save(storeEntity).getStoreNumber();
    }

    @Override
    public List<StoreDetailDTO> findAll() {
        List<StoreEntity> storeEntityList = sr.findAll();
        List<StoreDetailDTO> storeList = new ArrayList<>();
            for (StoreEntity se1: storeEntityList)  {
                storeList.add(toStoreDetailDTO(se1));
            }
        System.out.println("StoreSerivceImpl.categoryFindAll");
        return storeList;
    }

    //0214
    @Override
    public StoreDetailDTO findById(String storeName) {
        System.out.println("StoreSerivceImpl.findByStore");
        return StoreDetailDTO.toStoreDetailDTO(sr.findByStoreName(storeName));
    }

    @Override
    public void saveMenu(MenuDTO menuDTO, StoreCategoryEntity storeCategoryEntity) throws IOException {
        System.out.println("StoreSerivceImpl.menuSave");

            MultipartFile menuFile = menuDTO.getMenuFile();
            String menuFilename = menuFile.getOriginalFilename();
            menuFilename = System.currentTimeMillis() + menuFilename;

            String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + menuFilename;
            if (!menuFile.isEmpty())    {
                menuFile.transferTo(new File(savePath));
            }
            menuDTO.setMenuFilename(menuFilename);

        StoreEntity storeEntity = sr.findById(menuDTO.getStoreNumber()).get();
//        StoreCategoryEntity storeCategoryEntity = scr.findById(menuDTO.getStoreCategoryNumber()).get();
        MenuEntity menuEntity = MenuEntity.toSaveMenuEntity(menuDTO,storeEntity,storeCategoryEntity);

        mnr.save(menuEntity);
    }

    @Override
    public List<StoreCategoryDTO> categoryList() {
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail"));
        List<StoreCategoryDTO> storeCategoryDTOList = new ArrayList<>();
        if (!storeEntity.getStoreCategoryEntityList().isEmpty()) {
            for (StoreCategoryEntity c : storeEntity.getStoreCategoryEntityList()){
                storeCategoryDTOList.add(StoreCategoryDTO.toEntity(c));
            }
        }
        return storeCategoryDTOList;
    }

    @Override
    public StoreCategoryEntity categorySave(Long storeNumber, String storeCategoryName) {
        StoreCategoryDTO storeCategoryDTO = new StoreCategoryDTO();
        storeCategoryDTO.setStoreCategoryName(storeCategoryName);
        StoreEntity storeEntity = sr.findById(storeNumber).get();
        Long storeCategoryNumber = scr.save(StoreCategoryEntity.saveStoreCategory(storeCategoryDTO, storeEntity)).getStoreCategoryNumber();
        StoreCategoryEntity storeCategoryEntity = scr.findById(storeCategoryNumber).get();
        return storeCategoryEntity;
    }

    @Override
    public StoreCategoryEntity findCategory(Long storeCategoryNumber) {
        return scr.findById(storeCategoryNumber).get();
    }

    @Override
    public StoreDetailDTO findByNumber(Long storeNumber) {
        return StoreDetailDTO.toStoreDetailDTO(sr.findById(storeNumber).get());
    }


}
