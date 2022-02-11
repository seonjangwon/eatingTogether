package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/category/{bigCategoryNumber}")
    public String bigCategoryPage(@Validated Long bigCategoryNumber, Model model){
        List <StoreDetailDTO> storeList = ss.findAll();
        model.addAttribute("storeList", storeList);
        return "store/category";
    }


}
