package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.store.MenuDetailDTO;
import com.et.eatingtogether.dto.store.StoreCategoryDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.entity.StoreCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService ss;;
    private final HttpSession session;

    @GetMapping("/category")
    public String bigCategoryMain(Model model)   {
        System.out.println("StoreController.bigCategoryAll");

        List<BigCategoryDTO> bcList = ss.findAllBc();
        model.addAttribute("bcList",bcList);

        List<StoreDetailDTO> storeList = ss.findAll();
        model.addAttribute("storeList", storeList);
        //얘를 데려와야 정보를 가져오지않남? 단순 창띄우기니까 필요가 없을지도!
        return "store/categoryMain";
    }

    //지원
    //일단 findAll이라고 생각해보자.

        //요청받은 CategoryNumber에 대한 리스트를 띄우기 위해서는
        //BigCategory의 정보, StoreDetail을 가져와 띄워줘야한다.
/*    @GetMapping ("/category/{bcNumberTest}")
    public String bigCategoryPage1 (Model model) {
        List<StoreDetailDTO> storeList = ss.findAll();
        model.addAttribute("storeList", storeList);
        System.out.println(storeList);
        System.out.println("StoreController.bigCategoryPage");
        return "store/categorytest";
    }*/

    @GetMapping ("/category/{bigCategoryNumber}")
    public String bigCategoryPage (@PathVariable Long bigCategoryNumber, Model model) {

        List<StoreDetailDTO> storeList = ss.findAll();
        model.addAttribute("storeList", storeList);
        System.out.println("category/{bigCategoryNumber}");
        // return "store/category/" + bigCategoryNumber; 경로상의 문제일 수도 있다고해서.
        return "store/category";
    }

    // 0214 지원 정리를 좀 해보았음... findById
    @GetMapping ("/{storeNumber}")
    public String storeDetail(@PathVariable Long storeNumber, Model model)  {
        System.out.println("매장상세내용 띄우기");
        StoreDetailDTO storeDetailDTO = ss.findByNumber(storeNumber);
        model.addAttribute("storeList",storeDetailDTO);

        // 0216 메뉴띄우기
        List<MenuDTO> menuList = ss.menuFindAll(storeNumber);
        model.addAttribute("menuList",menuList);
        System.out.println(menuList);

        System.out.println(storeDetailDTO.getStoreName());
        System.out.println(storeDetailDTO);
        return "store/store";
    }


    // 0213 업체 로그인 후 관리페이지로 이동
    @GetMapping ("/menu")
    public String menuForm (Model model)    {
        System.out.println("addMenuForm");
        model.addAttribute("menuSave", new MenuDTO()); // 필드 생성용
        // 스토어카테고리
        List<StoreCategoryDTO> categoryDTOList = ss.categoryList();
        model.addAttribute("storeCategory",categoryDTOList);
        StoreDetailDTO storeList = ss.findById((String) session.getAttribute("storeLoginEmail"));
        model.addAttribute("storeNumber",storeList.getStoreNumber());
        return "store/menuSave";
    }
    // 모든것에 의미를 부여하지말자... 슬프다 똑똑해지고싶다 흑흑

    // 0214-15 지원 메뉴등록
    @PostMapping ("/menu")
    public String menu (@Validated @ModelAttribute("menuSave") MenuDTO menuDTO) throws IOException {
        System.out.println("StoreController.addMenu");
        StoreCategoryEntity storeCategoryEntity;
        if (menuDTO.getStoreCategoryNumber() == 0){
            storeCategoryEntity = ss.categorySave(menuDTO.getStoreNumber(),menuDTO.getStoreCategoryName());
        } else {
            storeCategoryEntity = ss.findCategory(menuDTO.getStoreCategoryNumber());
        }
        ss.saveMenu(menuDTO, storeCategoryEntity);
        return "redirect:/store/"+storeCategoryEntity.getStoreEntity().getStoreNumber();
    }

    //0216
    @PostMapping("/menuList")
    public @ResponseBody List<MenuDTO> menuAjax(@PathVariable Long storeNumber)  {
        List<MenuDTO> menuList = ss.menuFindAll(storeNumber);
        System.out.println("storeController.List<MenuDTO> menuAjax");
        return menuList;
    }

    //0217 헉 이거 아니다 아 아니 맞다
    @GetMapping ("/update/{menuNumber}")
    public String menuUpdateForm (@PathVariable Long menuNumber, Model model) {
        MenuDetailDTO menuDetail = ss.findByMenu(menuNumber);
        model.addAttribute("menu", menuDetail);
        System.out.println("StoreController.menuUpdateForm 실행");
        return "store/menuUpdate";
    }




}
