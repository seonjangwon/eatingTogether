package com.et.eatingtogether;

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
import com.et.eatingtogether.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class JiwonTest {
    @Autowired
    private StoreService ss;
    @Autowired
    private StoreRepository sr;
    @Autowired
    private BigCategoryRepository bcr;
    @Autowired
    private StoreCategoryRepository scr;
    @Autowired
    private MenuRepository mnr;
    //✔ 테스트에 앞서 의존성 주입을 진행


    @Test
    @DisplayName("회원데이터 생성")
    public void newMembers() {
    }

    @Test
    //@Transactional
    //@Rollback
    @DisplayName("메뉴추가 힘들어")
    public void SaveMenuTest()  {

        //업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreNumber(19L);
        Long storeNumber = sr.save(storeEntity).getStoreNumber();

        //스토어카테고리
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity);
        storeCategoryEntity.setStoreCategoryName("김밥류");
        Long storeCategoryNumber = scr.save(storeCategoryEntity).getStoreCategoryNumber();

        //메뉴
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity.setMenuName("SaveMenu");
        menuEntity.setMenuPrice(3000);
        Long menuNumber = mnr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity: "+menuEntity);


    }

}
