package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.service.SecurityService;
import com.et.eatingtogether.entity.DeliveryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
/*import org.jetbrains.annotations.NotNull;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/usual")
public class UsualController2 {
    //
    private final StoreService ss;
    private final SecurityService ses;
    private final HttpSession session;
    // 필요한 기능
    // 2-1 업체 회원가입요청, 2-2 업체 회원가입처리, 2-2-1 업체 가입 이메일 중복체크
    // 2-3 업체 로그인요청, 2-4 업체 로그인처리

    //2-1
    @GetMapping("/store")
    public String storeSaveForm (Model model){
        System.out.println("UsualController2.storeSaveForm");
        model.addAttribute("storeSave", new StoreSaveDTO());
        return "usual/storeSave";
    }
    //2-2
    @PostMapping("/store")
    public String storeSave ( @Validated @ModelAttribute("storeSave") StoreSaveDTO storeSaveDTO,BindingResult bindingResult) throws IOException {
        System.out.println("UsualController2.storeSave처리");
        System.out.println("storeSaveDTO="+storeSaveDTO);

        try {
//            Long storeId = ss.save(storeSaveDTO);
            Long storeId = ses.joinStore(storeSaveDTO);
        }   catch (IllegalStateException email) {
            bindingResult.reject("emailCheck", email.getMessage());
            //email.getMessage() 에는 serviceImpl에서 지정한 예외메세지가 담겨있다. serviceImpl 65번째 줄.
            return "usual/storeSave";
        }

        System.out.println("가입완료");
        return "usual/login";
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
                System.out.println(storeLoginDTO.getStoreEmail()+"으로 로그인성공");
                return "store/categoryMain";

            }   else {
                System.out.println("로그인실패");
                return "usual/storeLogin";
            }
    }

    //지원 0219
    @PostMapping("/idDuplicate")
    @ResponseBody //-
    public String storeEmailCheck (@RequestParam String storeEmail){
        System.out.println("storeEmail = " + storeEmail);
        String result = ss.idDuplicate(storeEmail);
        return result;
}

    //지원 0303 업체정보수정
    @GetMapping ("/supdate")
    public String storeUpdateForm(Model model)  {
        System.out.println("UsualController.storeUpdateForm 출력");
        model.addAttribute("storeUpdate", ss.findById((String)session.getAttribute("storeLoginEmail")));
        return "store/update";
    }

    @PutMapping("/supdate")
    @ResponseBody
    public String storeUpdate(@ModelAttribute StoreDetailDTO storeDetailDTO) throws IOException {
        System.out.println("UsualController.storeUpdate 처리요청");
//        String result = ss.updateStore(storeDetailDTO);
        String result = ses.updateStore(storeDetailDTO);
        System.out.println("result = " + result);
        return result;
    }

}
