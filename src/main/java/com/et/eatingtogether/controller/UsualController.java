package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.service.CustomerService;
import com.et.eatingtogether.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usual")
public class UsualController {
    // 하하하하하하하하하 sunyoung
    //jiwon1
    // jangwon

    private final CustomerService cs;
    private final SecurityService ss;

    @GetMapping("/customerLogin")
    public String customerLoginForm(Model model){
        model.addAttribute("customer",new CustomerDetailDTO());
        return "usual/customerLogin";
    }

    @PostMapping("/customerLogin")
    public String customerLogin(@Validated @ModelAttribute("customer") CustomerDetailDTO customerDetailDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "usual/customerLogin";
        }
        try {
            cs.login(customerDetailDTO);
        } catch (IllegalStateException e) {
            return "usual/customerLogin";
        }
        return "index";
    }



    // 회원가입
    @GetMapping("/customer")
    public String customerSaveForm(Model model){
        model.addAttribute("customer", new CustomerSaveDTO());
        return "usual/customerSave";
    }


    // 회원가입 처리
    @PostMapping("/customer")
    @ResponseBody
    public String customerSave(@ModelAttribute CustomerSaveDTO customerSaveDTO){
        // 테스트용 리턴갑 cNum
        Long cNum = cs.save(customerSaveDTO);
//        Long cNum = ss.joinCustomer(customerSaveDTO);

        return "ok";
    }

    // 이메일 중복확인
    @PostMapping("/customerEmail")
    public @ResponseBody
    String customerEmailCheck(@RequestParam("customerEmail") String customerEmail){
        String result =  cs.findByCustomerEmail(customerEmail);
        System.out.println("result = " + result);
        return result;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }

    //통합로그인
    @GetMapping("/loginForm")
    public String loginForm(){
        return "usual/login";
    }

//    @PostMapping("/login")
//    public String login(){
//        return "usual/login";
//    }

    @GetMapping("/loginResult")
    public String loginResult(){
        return "usual/loginResult";
    }
}
