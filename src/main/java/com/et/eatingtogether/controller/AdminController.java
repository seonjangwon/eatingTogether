package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerBlacklistDTO;
import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.store.StoreBlacklistDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.ReportSaveDTO;
import com.et.eatingtogether.dto.system.RiderDTO;
import com.et.eatingtogether.entity.OrderEntity;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("customerLoginEmail") // 선영 220223 : 리뷰 테스트용으로 작성함
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService as;
    private final CustomerService cs;
    private final HttpSession session;

    @GetMapping
    public String adminpage(Model model){
        return "admin/admin";
    }

    // 쿠폰등록 페이지로 이동
    @GetMapping("/coupon")
    public String couponForm(Model model){
        model.addAttribute("coupon", new CouponDTO());
        return "admin/couponSave";
    }

    // 쿠폰 저장
    @PostMapping("/coupon")
    public String coupon(@ModelAttribute CouponDTO couponDTO){
        as.save(couponDTO);
        return "redirect:/admin/coupon/";
    }

    // 쿠폰리스트 출력
    @GetMapping("/coupon/")
    public String couponList(Model model){
        List<CouponDTO> couponList = as.findAll();
        System.out.println("couponList = " + couponList);
        model.addAttribute("couponList", couponList);
        return "admin/couponList";
    }

    //라이더 등록 페이지로 이동
    @GetMapping("/rider")
    public String riderForm(Model model){
        model.addAttribute("rider", new RiderDTO());
        return "admin/riderSave";
    }

    // 라이더 등록처리
    @PostMapping("/rider")
    public String rider(@ModelAttribute RiderDTO riderDTO){
        as.riderSave(riderDTO);
        return "redirect:/admin/rider/";
    }

    // 라이더 리스트 출력
    @GetMapping("/rider/")
    public String riderList(Model model){
        List<RiderDTO> riderList = as.riderFindAll();
        model.addAttribute("riderList", riderList);
        return "admin/riderList";
    }

    // 회원목록 조회
    @GetMapping("/customer")
    public String customerList(Model model){
        List<CustomerDetailDTO> customerList = as.CustomerFindAll();
        model.addAttribute("customerList", customerList);
        return "admin/customerList";
    }

    // 회원삭제(ajax)
    @DeleteMapping("/{customerNumber}")
    public ResponseEntity customerDelete(@PathVariable("customerNumber") Long customerNum){
        as.customerDelete(customerNum);
        return new ResponseEntity(HttpStatus.OK);

    }

//    // 회원삭제
//    @DeleteMapping("/{customerNumber}")
//    public String customerDelete(@PathVariable("customerNumber") Long customerNum){
//       as.customerDelete(customerNum);
//      return "redirect:/admin/customer";
//
//    }




    // 업체목록
    @GetMapping("/store")
    public String storeList(Model model){
        List<StoreDetailDTO> storeList = as.storeFindAll();
        model.addAttribute("storeList", storeList);
        return "admin/storeList";
    }

    // 업체삭제
    @DeleteMapping("/store/{storeNumber}")
    public ResponseEntity storeDelete(@PathVariable("storeNumber") Long storeNum){
        as.storeDelete(storeNum);
        return new ResponseEntity(HttpStatus.OK);
    }


    // 회원신고 페이지로 이동
    @GetMapping("/customerReport/{customerNumber}")
    public String reportForm(Model model, @PathVariable("customerNumber") Long customerNumber){
        model.addAttribute("customerNumber", customerNumber);
        return "admin/customerReport";
    }

    // 회원 신고저장
    @PostMapping("/customerReport")
    public @ResponseBody String customerReport(@ModelAttribute CustomerBlacklistDTO customerBlacklistDTO){
        System.out.println("customerBlacklistDTO = " + customerBlacklistDTO);
        as.reportSave(customerBlacklistDTO);
        return "ok";
    }

    // 업체사유신고 페이지로 이동
    @GetMapping("/storeReport/{storeNumber}")
    public String storeReportForm(Model model, @PathVariable("storeNumber") Long storeNumber){
        model.addAttribute("storeNumber", storeNumber);
        return "admin/storeReport";
    }

    // 업체 신고 저장
    @GetMapping("/sblackList")
    public ResponseEntity storeReport(@ModelAttribute StoreBlacklistDTO storeBlacklistDTO){
        System.out.println("storeBlacklistDTO = " + storeBlacklistDTO);
        as.storeReportSave(storeBlacklistDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    //블랙리스트 페이지 이동
    @GetMapping("/blackList")
    public String blackListForm(Model model){
        List<CustomerBlacklistDTO> cblackList = as.cblackList();
        model.addAttribute("customerBlackList", cblackList);
        return "admin/blackList";
    }


    // 업체 리뷰관리 페이지로 이동
    @GetMapping("/storeReview")
    public String storeReviewForm(Model model){
        List<ReviewDetailDTO> reviewDetailDTOList = as.reviewFindAll();
        model.addAttribute("reviewList", reviewDetailDTOList);
        return "store/review";
    }




}
