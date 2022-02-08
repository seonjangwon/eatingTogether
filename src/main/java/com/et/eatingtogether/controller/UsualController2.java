package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
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

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usual")
public class UsualController2 {
    private final StoreService ss;
    // 필요한 기능
    // 2-1 업체 회원가입요청, 2-2 업체 회원가입처리, 2-2-1 업체 가입 이메일 중복체크
    // 2-3 업체 로그인요청, 2-4 업체 로그인처리

    //2-1
    @GetMapping("/store")
    public String storeSaveForm (){
        System.out.println("UsualController2.storeSaveForm");
        return "usual/storeSave";
    }

    @PostMapping("/store")
    public String storeSave (@ModelAttribute StoreSaveDTO storeSaveDTO) throws IOException {
        System.out.println("UsualController2.storeSave처리");

        Long storeId = ss.save(storeSaveDTO);
        return "/store/deliverySave";
    }

    //2-3
    @GetMapping("/slogin")
    public String storeLoginForm (Model model)  {
        model.addAttribute("store", new StoreDetailDTO());
        return "usual/storeLogin";
    }

    //2-4
    @PostMapping("/slogin")
    public String storeLogin (@ModelAttribute StoreLoginDTO storeLoginDTO, HttpSession session)   {
        System.out.println("UsualController2.storeLogin");

        boolean loginResult = ss.login(storeLoginDTO);
            if(loginResult) {
                session.setAttribute("storeLoginEmail", storeLoginDTO.getStoreEmail());
                System.out.println("로그인성공");
                return "redirect:/store/orderNow";
                /*String redirectURL = (String) session.getAttribute("redirectURL");
*/
                /*if(redirectURL != null) {
                    return "redirect:/" + redirectURL;
                } else {
                    return "redirect:/";
                }*/
            }   else {
                System.out.println("로그인실패");
                return "usual/storeLogin";
            }
    }



}
