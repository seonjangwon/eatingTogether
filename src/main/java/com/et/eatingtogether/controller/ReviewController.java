package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.review.ReviewSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {


    // 리뷰 작성 페이지로 이동
    @GetMapping("/save")
    public String reviewSaveForm(Model model){
        model.addAttribute("review", new ReviewSaveDTO());
        return "customer/reviewSave";

    }
}
