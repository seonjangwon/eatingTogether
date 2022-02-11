package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usual")
public class UsualController {
    // 하하하하하하하하하 sunyoung
    //jiwon1
    // jangwon

    private final CustomerService cs;

    @GetMapping("/customerLogin")
    public String customerLoginForm(Model model){
        model.addAttribute("customer",new CustomerDetailDTO());
        return "usual/customerLogin";
    }

    @PostMapping("/customerLogin")
    public String customerLogin(@Validated @ModelAttribute("customer") CustomerDetailDTO customerDetailDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "usual/cutomerLogin";
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
}
