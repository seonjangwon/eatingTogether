package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.review.ReviewSaveDTO;
import com.et.eatingtogether.dto.review.ReviewTestDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.entity.ReviewEntity;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final AdminService as;
    private final StoreService ss;
    private final CustomerService cs;





    // 리뷰 작성 페이지로 이동
    @GetMapping("/save")
    public String reviewSaveForm(Model model){
        model.addAttribute("review", new ReviewSaveDTO());
        return "customer/reviewSave";
    }

    // 리뷰 등록 처리
    @PostMapping("/save")
    public String review(@ModelAttribute ReviewSaveDTO reviewSaveDTO,
                         @RequestParam(value = "reviewFileDTO",required = false) MultipartFile[] reviewFileDTOList,
                         MultipartHttpServletRequest request
                         ) throws IOException {



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
//        for (ReviewFileDTO r : reviewSaveDTO.getReviewFileDTOList()){
//            as.reviewSave1(r,reviewSaveDTO);
//        }
        return "redirect:/review/";

//        return new ResponseEntity(HttpStatus.OK);

    }


    // 리뷰 등록 후 페이지 이동
    @GetMapping("/")
    public String reviewFinish(Model model){
        List<ReviewDetailDTO> reviewList = as.reviewFindAll();
        model.addAttribute("reviewList", reviewList);
        return "customer/review";
    }




}
