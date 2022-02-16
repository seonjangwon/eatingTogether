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
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.et.eatingtogether.dto.store.MenuDTO.toMenuDetailDTO;
import static com.et.eatingtogether.dto.store.StoreDetailDTO.toStoreDetailDTO;
import static com.et.eatingtogether.dto.system.BigCategoryDTO.toBCDetailDTO;

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
        System.out.println("StoreServiceImpl.storeSave");

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

        BigCategoryEntity bigCategoryEntity = bcr.findByBigCategoryNumber(storeSaveDTO.getBigCategoryNumber());
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
        System.out.println("StoreServiceImpl.categoryFindAll");
        return storeList;
    }

    @Override
    public List<BigCategoryDTO> findAllBc() {
        List<BigCategoryEntity> bigCategoryEntityList = bcr.findAll();
        List<BigCategoryDTO> bcList = new ArrayList<>();
            for (BigCategoryEntity bce: bigCategoryEntityList)  {
                bcList.add(toBCDetailDTO(bce));
            }
        System.out.println("ServiceImpl.findAllBc.해치웠나");
        return bcList;
    }


    // store/category/{bigCategoryNumber} 하기위해.



    //0214
    @Override
    public StoreDetailDTO findById(String storeName) {
        System.out.println("StoreServiceImpl.findByStore");
        return StoreDetailDTO.toStoreDetailDTO(sr.findByStoreName(storeName));
    }

    @Override
    public void saveMenu(MenuDTO menuDTO, StoreCategoryEntity storeCategoryEntity) throws IOException {
        System.out.println("StoreServiceImpl.menuSave");

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
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail")); //session은 object class로 값을 가져오기때문에 String으로 형변환을 해준다.
        List<StoreCategoryDTO> storeCategoryDTOList = new ArrayList<>();    // List<스토어카테고리의 정보가 있는> storeCategoryDTOList를 새로이 List화 시킨다.
        if (!storeEntity.getStoreCategoryEntityList().isEmpty()) {          // if, storeEntity의 StoreCategoryEntityList가 Empty가 아닌 경우
            for (StoreCategoryEntity c : storeEntity.getStoreCategoryEntityList()){ // for문을 이용해 storeEntity의 StoreCategryEntityList를 c에 담는다.
                storeCategoryDTOList.add(StoreCategoryDTO.toEntity(c));     // StoreCategoryDTO.toEntity(c) 라는 값을. storeCategoryDTOList에 add-추가한다.
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

    @Override
    public List<MenuDTO> menuFindAll(Long storeNumber) {
        StoreEntity storeEntity = sr.findById(storeNumber).get();
        List<MenuEntity> menuEntityList = mnr.findByStoreEntity(storeEntity);

        List<MenuDTO> menuList = new ArrayList<>();
        for(MenuEntity menu: menuEntityList)    {
            menuList.add(toMenuDetailDTO(menu));
        }
        return menuList;
    }


    //위에꺼지우기
    /*
    @Override //Menu findAll
    public List<MenuDTO> menuFindAll(Long storeNumber) {
        *//*List<MenuEntity> menuEntityList = mnr.findByStoreNumber();*//*
        List<MenuEntity> menuEntityList = mnr.findByStoreEntity(StoreEntity storeEntity);

        *//*MenuEntity menuEntityList = mnr.findById(MenuEntity.toSaveMenuEntity().getStoreEntity().getStoreNumber()).get();*//*
        *//*MenuEntity menuEntity = mnr.findById(storeNumber).get();*//*
        List<MenuDTO> menuList = new ArrayList<>();
        for(MenuEntity menu: menuEntityList)    {
            menuList.add(toMenuDetailDTO(menu));
        }
        return menuList;
    }*/
    /*
    *     public List<StoreDetailDTO> findAll() {
        List<StoreEntity> storeEntityList = sr.findAll();
        List<StoreDetailDTO> storeList = new ArrayList<>();
            for (StoreEntity se1: storeEntityList)  {
                storeList.add(toStoreDetailDTO(se1));
            }
        System.out.println("StoreServiceImpl.categoryFindAll");
        return storeList;
    }*/


}
