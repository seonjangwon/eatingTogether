package com.et.eatingtogether.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping ("/storeMain")
    public String storeMain()   {
        return "storeMain";
    }
}
