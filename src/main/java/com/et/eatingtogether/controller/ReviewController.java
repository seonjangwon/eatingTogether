package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.review.ReviewSaveDTO;
import com.et.eatingtogether.entity.ReviewEntity;
import com.et.eatingtogether.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final AdminService as;

    // 리뷰 작성 페이지로 이동
    @GetMapping("/save")
    public String reviewSaveForm(Model model){
        model.addAttribute("review", new ReviewSaveDTO());
        return "customer/reviewSave";
    }

    // 리뷰 등록 처리
    @PostMapping("/")
    public String review(ReviewSaveDTO reviewSaveDTO){
        as.reviewSave(reviewSaveDTO);
        List<ReviewEntity> reviewList = as.reviewFindAll();
        return "redirect:/review/";
    }

    // 리뷰목록 출력




}
