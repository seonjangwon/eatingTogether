package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private StoreService ss;

  //큰 카테고리 선택 가능
  @GetMapping("/category")
  public String bicCategoryMain (){
    System.out.println("StoreController.cmain 띄우기");
    return "/store/category";
  }
  //큰 카테고리에서 메뉴 선택시
  /*@GetMapping("/category/{categoryNum}")*/
  @GetMapping("/category/{bigCategoryNumber}")
    public String bigCategoryList(@PathVariable Long bigCategoryNumber, Model model){
      System.out.println("StoreController.cmain");
      StoreDetailDTO store = ss.findById(bigCategoryNumber);
      model.addAttribute("store",store);
      /*List<StoreDetailDTO> storeList = ss.findAll();*/
      /*model.addAttribute("storeList", storeList);*/
      return "/store/selectCategory";
  }
}
