package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usual")
public class UsualController2 {
    private final StoreService ss;
    // 필요한 기능
    // 2-1 업체 회원가입요청, 2-2 업체 회원가입처리, 2-2-1 업체 가입 이메일 중복체크
    // 2-3 업체 로그인요청, 2-4 업체 로그인처리

    @GetMapping("/storeLogin")
    public String storeLoginForm (Model model)  {
        model.addAttribute("store", new StoreDetailDTO());
        return "usual/storeLogin";
    }




}
