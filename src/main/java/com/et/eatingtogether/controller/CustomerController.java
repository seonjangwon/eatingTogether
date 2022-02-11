package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.*;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;
import com.et.eatingtogether.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/history")
    public String historyList(Model model){
        List<OrderDTO> orderDTOList = cs.orderList();
        model.addAttribute("orderList",orderDTOList);
        return "customer/orderList";
    }

    @GetMapping("/history/{orderNumber}")
    public String history(@PathVariable("orderNumber") Long orderNumber, Model model){
        OrderDTO orderDTO = cs.findOrder(orderNumber);
        List<OrderMenuDTO> menuDTOList = cs.orderMenu(orderNumber);
        CustomerDetailDTO customerDetailDTO = cs.findById(orderDTO.getCustomerNumber());
        model.addAttribute("order",orderDTO);
        model.addAttribute("menuList",menuDTOList);
        model.addAttribute("customer",customerDetailDTO);
        return "customer/order";
    }

    @GetMapping("/coupon")
    public String coupon(Model model){
        List<MyCouponDTO> couponDTOList = cs.couponList();
        model.addAttribute("couponList",couponDTOList);
        return "customer/coupon";
    }

    @GetMapping("/point")
    public String point(Model model){
        List<PointDTO> pointDTOList = cs.pointList();
        CustomerDetailDTO customerDetailDTO = cs.findById(pointDTOList.get(0).getCustomerNumber());
        model.addAttribute("pointList",pointDTOList);
        model.addAttribute("point",customerDetailDTO.getCustomerPoint());
        return "customer/point";
    }

    @GetMapping("/review")
    public String myReview(Model model){
        List<ReviewDetailDTO> reviewDetailDTOList = cs.reviewList();
        model.addAttribute("reviewList",reviewDetailDTOList);
        return "customer/review";
    }

    @GetMapping("/wishlist")
    public String myWishlist(Model model){
        List<WishlistDTO> wishlistDTOList = cs.wishlist();
        model.addAttribute("wishlist",wishlistDTOList);
        return "customer/wishlist";
    }

    @GetMapping("/basket")
    public String myBasket(Model model){
        List<BasketDTO> basketDTOList = cs.basketList();
        String storeName = basketDTOList.get(0).getStoreName();
        int totalPrice = 0;
        for (BasketDTO b : basketDTOList){
            totalPrice += b.getMenuPrice() * b.getMenuCount();
        }
        int deliveryPrice = cs.deliveryPrice(basketDTOList.get(0).getStoreNumber());
        model.addAttribute("basketList",basketDTOList);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("deliveryPrice",deliveryPrice);
        return "customer/basket";
    }
    @PutMapping("/menu")
    @ResponseBody
    public String menuUpDown(@RequestParam("basketNumber") Long basketNumber,
                             @RequestParam("type") String type){
        String result = cs.menuUpDown(basketNumber,type);
        return result;
    }

    @DeleteMapping("/menu")
    @ResponseBody
    public String menuDelete(@RequestParam("basketNumber") Long basketNumber){
        cs.menuDelete(basketNumber);
        return "ok";
    }

    @GetMapping("/payment")
    public String paymentForm(Model model){
        // 내 정보 (주소 연락처 포인트)
        // 요청사항
        // 결제 수단
        // 쿠폰 갯수, 리스트
        // 결제 금액 주문금액 배달팁 총액
        return "";
    }
}
