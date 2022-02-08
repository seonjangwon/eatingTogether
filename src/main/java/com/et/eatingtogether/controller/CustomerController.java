package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService cs;
    private final HttpSession session;

    @GetMapping("/")
    public String myPage(Model model){
        model.addAttribute("customer", cs.findByEmail((String)session.getAttribute("customerLoginEmail")));
        return "customer/mypage";
    }

    @GetMapping("/update")
    public String updateForm(Model model){
        model.addAttribute("customer", cs.findByEmail((String)session.getAttribute("customerLoginEmail")));
        return "customer/update";
    }

    @PutMapping("/update")
    public String updateForm(@ModelAttribute CustomerDetailDTO customerDetailDTO){
        return cs.update(customerDetailDTO);
    }

}
