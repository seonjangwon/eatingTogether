package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final StoreService ss;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping ("/storeMain")
    public String storeMain(Model model)   {
//        System.out.println("storeMain");
//        List<StoreDetailDTO> store = ss.findAll();
//        model.addAttribute("store",store);

        return "storeMain";
    }
}
