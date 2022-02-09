package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService ss;
    private final BigCategoryRepository bcr;

/*    @PostMapping("./storeMain")
    public ResponseEntity<String> insertBc (){
        BigCategoryEntity bc1 = new BigCategoryEntity("한식",1L);
        BigCategoryEntity bc2 = new BigCategoryEntity("중식",2L);
        BigCategoryEntity bc3 = new BigCategoryEntity("일식",3L);
        BigCategoryEntity bc4 = new BigCategoryEntity("양식",4L);
        BigCategoryEntity bc5 = new BigCategoryEntity("패스트푸드,기타",5L);

        List<BigCategoryEntity> bigCategoryEntities = new ArrayList<>();
        bcr.saveAll(bigCategoryEntities);
        System.out.println("일단ㅋ");
        return new ResponseEntity(HttpStatus.OK);
    }*/


}
