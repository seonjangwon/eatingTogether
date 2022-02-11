package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usual1")
public class UsualController1 {
    // branch add
    private final CustomerService cs;



    // 회원가입
    @GetMapping("/customer")
    public String customerSaveForm(Model model){
        model.addAttribute("customer", new CustomerSaveDTO());
        return "usual/customerSave";
    }


    // 회원가입 처리
    @PostMapping("/customer")
    public String customerSave(@ModelAttribute CustomerSaveDTO customerSaveDTO){
        // 테스트용 리턴갑 cNum
        Long cNum = cs.save(customerSaveDTO);

        return "usual/customerLogin";
    }

    // 이메일 중복확인
    @PostMapping("/customerEmail")
    public @ResponseBody String customerEmailCheck(@RequestParam("customerEmail") String customerEmail){
       String result =  cs.findByCustomerEmail(customerEmail);
        System.out.println("result = " + result);
        return result;
    }

    //-------- 여기까지 UsualController로 복사 완료 -------------

//    // 로그아웃
//    @GetMapping("/logout")
//    public String logout(HttpSession session){
//        session.invalidate();
//    }

}
