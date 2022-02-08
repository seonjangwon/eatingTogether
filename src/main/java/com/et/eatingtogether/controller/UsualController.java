package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.service.CustomerService;
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
public class UsualController {
    // 하하하하하하하하하 sunyoung
    //jiwon
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
}
