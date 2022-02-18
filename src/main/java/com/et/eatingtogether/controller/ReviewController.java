package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.review.ReviewSaveDTO;
import com.et.eatingtogether.dto.review.ReviewTestDTO;
import com.et.eatingtogether.entity.ReviewEntity;
import com.et.eatingtogether.service.AdminService;
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

    // 리뷰 작성 페이지로 이동
    @GetMapping("/save")
    public String reviewSaveForm(){
        return "customer/reviewSave";
    }

    // 리뷰 등록 처리
    @PostMapping("/save")
    @ResponseBody
    public String review(@RequestParam("reviewScore") int reviewScore,
                         @RequestParam("reviewContents") String reviewContents,
                         @RequestParam(value = "reviewFileDTOList",required = false) MultipartFile[] reviewFileDTOList,
                         MultipartHttpServletRequest request
                         ) throws IOException {
        System.out.println("reviewScore = " + reviewScore);
        System.out.println("reviewContents = " + reviewContents);
        System.out.println("reviewFileDTOList = " + reviewFileDTOList.length);
        ReviewSaveDTO reviewSaveDTO = new ReviewSaveDTO();
        reviewSaveDTO.setReviewScore(reviewScore);
        reviewSaveDTO.setReviewContents(reviewContents);

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
        List<ReviewTestDTO> reviewList = as.reviewFindAll();
        model.addAttribute("reviewList", reviewList);
        return "customer/review";
    }




}
