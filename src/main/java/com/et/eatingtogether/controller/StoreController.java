package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService ss;
    private final BigCategoryRepository bcr;

    @GetMapping("/category")
    public String bigCategoryMain(Model model)   {
        System.out.println("StoreController.bigCategoryAll");
        /*model.addAttribute("bcAll", new StoreSaveDTO());*/
        //얘를 데려와야 정보를 가져오지않남? 단순 창띄우기니까 필요가 없을지도!
        return "store/categoryMain";
    }

    //지원
    //일단 findAll이라고 생각해보자.
    @GetMapping ("/category/{bigCategoryNumber}")
    public String bigCategoryPage1 (Model model) {
        //요청받은 CategoryNumber에 대한 리스트를 띄우기 위해서는
        //BigCategory의 정보, StoreDetail을 가져와 띄워줘야한다.

        List<StoreDetailDTO> storeList = ss.findAll();;
        model.addAttribute("storeList", storeList);
        System.out.println(storeList);
        System.out.println("StoreController.bigCategoryPage");
        return "store/category";
    }

    // 0213 업체 로그인 후 관리페이지로 이동
    @GetMapping ("/menu")
    public String addMenuForm ()    {
        System.out.println("addMenuForm");
        return "store/menuSave";
    }

    // 0214 지원 정리를 좀 해보았음... findById
    @GetMapping ("/{storeName}")
    public String storeDetail(@PathVariable String storeName, Model model)  {
        System.out.println("매장상세내용 띄우기");
        StoreDetailDTO storeList = ss.findById(storeName);
        model.addAttribute("storeList",storeList);
        System.out.println(storeList.getStoreName());
        System.out.println(storeList);
        return "store/store";
    }

}
