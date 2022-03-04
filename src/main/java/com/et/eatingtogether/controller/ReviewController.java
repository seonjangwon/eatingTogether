package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerBlacklistDTO;
import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.review.*;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.entity.CustomerBlacklistEntity;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.OrderEntity;
import com.et.eatingtogether.entity.ReviewEntity;
import com.et.eatingtogether.repository.CustomerRepository;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("customerLoginEmail") // 선영 220223 : 리뷰테스트용
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final AdminService as;
    private final StoreService ss;
    private final CustomerService cs;
    private final CustomerRepository cr;
    private final HttpSession session;


    // 리뷰 작성 페이지로 이동 0222, 0301
    @GetMapping("/save/{orderNumber}")
    public String reviewSaveForm(Model model, @PathVariable("orderNumber") Long orderNum){
        // 여기서 회원번호, 가게이름, 메뉴이름을 같이 넘겨줘야 함
        OrderDTO order = cs.findOrder(orderNum);
        model.addAttribute("order", order);
        return "customer/reviewSave";
    }


    @PostMapping("/save")
    public String review(@ModelAttribute ReviewSaveDTO reviewSaveDTO,
                         @RequestParam(value = "reviewFileDTO",required = false) MultipartFile[] reviewFileDTOList,
                         MultipartHttpServletRequest request
                         ) throws IOException {

    // 리뷰 내용 등을 담은 ReviewSaveDTO와 파일을 담은 ReviewFileDTO를 함께 보내줌.

        System.out.println("reviewFileDTOList = " + reviewFileDTOList.length);
        System.out.println("reviewSaveDTO = " + reviewSaveDTO);

        List<ReviewFileDTO> reviewFileDTOS =new ArrayList<>();

        for (int i = 0; i < reviewFileDTOList.length; i++){
            ReviewFileDTO reviewFileDTO = new ReviewFileDTO();
            reviewFileDTO.setReviewFile(reviewFileDTOList[i]);
            reviewFileDTOS.add(reviewFileDTO);
        }

        reviewSaveDTO.setReviewFileDTOList(reviewFileDTOS);
        System.out.println("reviewSaveDTO = " + reviewSaveDTO);
        System.out.println("reviewFileDTOS = " + reviewFileDTOS);
        System.out.println("reviewSaveDTO.getReviewFileDTOList = " + reviewSaveDTO.getReviewFileDTOList());
        as.reviewSave(reviewSaveDTO);

        return "redirect:/customer/";


    }


    // 리뷰 등록 후 페이지 이동( 내가 쓴 리뷰목록)
    @GetMapping("/")
    public String reviewFinish(Model model){
        List<ReviewDetailDTO> reviewList = as.reviewFindAll();
        model.addAttribute("reviewList", reviewList);
        return "store/store";
    }



    // 업체 리뷰관리 페이지로 이동
    @GetMapping("/store")
    public String storeReviewPage(Model model){
        List<ReviewDetailDTO> reviewDetailDTOList = as.reviewFindAll();
        System.out.println("reviewDetailDTOList = " + reviewDetailDTOList);
        model.addAttribute("reviewList", reviewDetailDTOList);
        return "store/review";
    }


    // 업체가 작성한 리뷰 등록
    @PostMapping("/reply")
    public String replySave(@ModelAttribute ReplySaveDTO replySaveDTO) {
        System.out.println("replySaveDTO = " + replySaveDTO);
        as.replySave(replySaveDTO);
        
        return "redirect:/review/store";
    }

}
