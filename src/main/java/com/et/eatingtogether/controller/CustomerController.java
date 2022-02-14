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
        if (!basketDTOList.isEmpty()) {
            String storeName = basketDTOList.get(0).getStoreName();
            int totalPrice = 0;
            for (BasketDTO b : basketDTOList){
                totalPrice += (b.getMenuPrice() * b.getMenuCount());
                System.out.println("b.getMenuPrice() = " + b.getMenuPrice());
                System.out.println("b.getMenuCount() = " + b.getMenuCount());
            }
            int deliveryPrice = cs.deliveryPrice(basketDTOList.get(0).getStoreNumber());
            model.addAttribute("basketList",basketDTOList);
            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("deliveryPrice",deliveryPrice);
        }
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
        CustomerDetailDTO customerDetailDTO = cs.findByEmail((String) session.getAttribute("customerLoginEmail"));
        model.addAttribute("customer",customerDetailDTO); // 정보용
        // 요청사항 이거는 여기서 안하지...? 모달로 필드만
        // 결제 수단 이거도 모달로 필드만
        model.addAttribute("order",new OrderDTO()); // 필드 출력용
        // 쿠폰 갯수, 리스트
        List<MyCouponDTO> myCouponDTOList = cs.couponList();
        model.addAttribute("coupon",myCouponDTOList); // 쿠폰 출력용
        // 결제 금액 주문금액 배달팁 총액
        List<BasketDTO> basketDTOList = cs.basketList();
        Long storeNumber = basketDTOList.get(0).getStoreNumber();
        int totalPrice = 0;
        String menuList = "";
        for (BasketDTO b : basketDTOList){
            totalPrice += b.getMenuPrice() * b.getMenuCount();
            menuList += b.getMenuName();
        }
        int deliveryPrice = cs.deliveryPrice(basketDTOList.get(0).getStoreNumber());
        model.addAttribute("storeNumber",storeNumber);
        model.addAttribute("menuList",menuList);
        model.addAttribute("totalPrice",totalPrice); // 장바구니 총 금액
        model.addAttribute("deliveryPrice",deliveryPrice); // 배달비 금액
        return "customer/payment";
    }

    @GetMapping("/kakaoPayTest")
    public String kakaoPayTest(){
        return "customer/kakaoPayTest";
    }

    @PostMapping("/payment")
    public String payment(@ModelAttribute OrderDTO orderDTO,@RequestParam("pointUse") int pointUse){
        System.out.println("orderDTO = " + orderDTO);
        System.out.println("pointUse = " + pointUse);
        // 주문 저장
        Long orderNumber = cs.orderSave(orderDTO);
        // 포인트 사용
        if(pointUse > 0){
            cs.pointUse(pointUse,orderNumber);
        }
        // 포인트 적립
        cs.pointAdd(orderNumber);
        // 장바구니 삭제
//        cs.basketDeleteAll();
        return "redirect:/customer/history/"+orderNumber;// 주문 번호 추가 하기
        //return "index";
    }
}
