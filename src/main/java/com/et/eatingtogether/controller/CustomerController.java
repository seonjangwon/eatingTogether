package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.*;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService cs;
    private final AdminService as;
    private final StoreService ss;
    private final HttpSession session;

    @GetMapping("/")
    public String myPage(Model model) {
        model.addAttribute("customer", cs.findByEmail((String) session.getAttribute("customerLoginEmail")));
        return "customer/mypage";
    }

    @GetMapping("/update")
    public String updateForm(Model model) {
        model.addAttribute("customer", cs.findByEmail((String) session.getAttribute("customerLoginEmail")));
        return "customer/update";
    }

    @PutMapping("/update")
    public String update(@ModelAttribute CustomerDetailDTO customerDetailDTO) {
        return cs.update(customerDetailDTO);
    }

    @GetMapping("/history")
    public String historyList(Model model) {
        List<OrderDTO> orderDTOList = cs.orderList();
        model.addAttribute("orderList", orderDTOList);
        return "customer/orderList";
    }

    @GetMapping("/history/{orderNumber}")
    public String history(@PathVariable("orderNumber") Long orderNumber, Model model) {
        OrderDTO orderDTO = cs.findOrder(orderNumber);
        List<OrderMenuDTO> menuDTOList = cs.orderMenu(orderNumber);
        CustomerDetailDTO customerDetailDTO = cs.findById(orderDTO.getCustomerNumber());
        model.addAttribute("order", orderDTO);
        model.addAttribute("menuList", menuDTOList);
        model.addAttribute("customer", customerDetailDTO);
        return "customer/order";
    }

    @GetMapping("/coupon")
    public String coupon(Model model) {
        List<MyCouponDTO> couponDTOList = cs.couponList();
        model.addAttribute("couponList", couponDTOList);
        return "customer/coupon";
    }

    @GetMapping("/point")
    public String point(Model model) {
        List<PointDTO> pointDTOList = cs.pointList();
        CustomerDetailDTO customerDetailDTO = cs.findById(pointDTOList.get(0).getCustomerNumber());
        model.addAttribute("pointList", pointDTOList);
        model.addAttribute("point", customerDetailDTO.getCustomerPoint());
        return "customer/point";
    }

    @GetMapping("/review")
    public String myReview(Model model) {
        List<ReviewDetailDTO> reviewDetailDTOList = cs.reviewList();
        model.addAttribute("reviewList", reviewDetailDTOList);
        return "customer/review";
    }

    @GetMapping("/wishlist")
    public String myWishlist(Model model) {
        List<WishlistDTO> wishlistDTOList = cs.wishlist();
        model.addAttribute("wishlist", wishlistDTOList);
        return "customer/wishlist";
    }

    @GetMapping("/basket")
    public String myBasket(Model model) {
        List<BasketDTO> basketDTOList = cs.basketList();
        if (!basketDTOList.isEmpty()) {
            String storeName = basketDTOList.get(0).getStoreName();
            int totalPrice = 0;
            for (BasketDTO b : basketDTOList) {
                totalPrice += (b.getMenuPrice() * b.getMenuCount());
                System.out.println("b.getMenuPrice() = " + b.getMenuPrice());
                System.out.println("b.getMenuCount() = " + b.getMenuCount());
            }
            int deliveryPrice = cs.deliveryPrice(basketDTOList.get(0).getStoreNumber());
            model.addAttribute("basketList", basketDTOList);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("deliveryPrice", deliveryPrice);
        }
        return "customer/basket";
    }

    @PutMapping("/menu")
    @ResponseBody
    public String menuUpDown(@RequestParam("basketNumber") Long basketNumber,
                             @RequestParam("type") String type) {
        String result = cs.menuUpDown(basketNumber, type);
        return result;
    }

    @DeleteMapping("/menu")
    @ResponseBody
    public String menuDelete(@RequestParam("basketNumber") Long basketNumber) {
        cs.menuDelete(basketNumber);
        return "ok";
    }

    @GetMapping("/payment")
    public String paymentForm(Model model) {
        // 내 정보 (주소 연락처 포인트)
        CustomerDetailDTO customerDetailDTO = cs.findByEmail((String) session.getAttribute("customerLoginEmail"));
        model.addAttribute("customer", customerDetailDTO); // 정보용
        // 요청사항 이거는 여기서 안하지...? 모달로 필드만
        // 결제 수단 이거도 모달로 필드만
        model.addAttribute("order", new OrderDTO()); // 필드 출력용
        // 쿠폰 갯수, 리스트
        List<MyCouponDTO> myCouponDTOList = cs.couponList();
        model.addAttribute("coupon", myCouponDTOList); // 쿠폰 출력용
        // 결제 금액 주문금액 배달팁 총액
        List<BasketDTO> basketDTOList = cs.basketList();
        Long storeNumber = basketDTOList.get(0).getStoreNumber();
        int totalPrice = 0;
        String menuList = "";
        for (BasketDTO b : basketDTOList) {
            totalPrice += b.getMenuPrice() * b.getMenuCount();
            menuList += b.getMenuName();
        }
        int deliveryPrice = cs.deliveryPrice(basketDTOList.get(0).getStoreNumber());
        model.addAttribute("storeNumber", storeNumber);
        model.addAttribute("menuList", menuList);
        model.addAttribute("totalPrice", totalPrice); // 장바구니 총 금액
        model.addAttribute("deliveryPrice", deliveryPrice); // 배달비 금액
        return "customer/payment";
    }

    @GetMapping("/kakaoPayTest")
    public String kakaoPayTest() {
        return "customer/kakaoPayTest";
    }

    @PostMapping("/payment")
    public String payment(@ModelAttribute OrderDTO orderDTO, @RequestParam("pointUse") int pointUse) {
        System.out.println("orderDTO = " + orderDTO);
        System.out.println("pointUse = " + pointUse);
        // 주문 저장
        Long orderNumber = cs.orderSave(orderDTO);
        // 포인트 사용
        if (pointUse > 0) {
            cs.pointUse(pointUse, orderNumber);
        }
        // 포인트 적립
        cs.pointAdd(orderNumber);
        // 장바구니 삭제
//        cs.basketDeleteAll();
        return "redirect:/customer/history/" + orderNumber;// 주문 번호 추가 하기
        //return "index";
    }

    @GetMapping("/kakaoLogin")
    public String kakaoLogin(@RequestParam("code") String code, Model model) {
        System.out.println("code = " + code);
        String access_token = cs.getAccessToken(code);
        System.out.println("access_token = " + access_token);
        HashMap<String, String> userInfo = cs.getUserInfo(access_token);
        CustomerDetailDTO customerDetailDTO = cs.findByEmail(userInfo.get("id"));
        System.out.println("customerDetailDTO = " + customerDetailDTO);
        session.setAttribute("customerLoginEmail", userInfo.get("id"));
        session.setAttribute("access_token", access_token); // 로그아웃 또는 연결 해제에 사용
        if (customerDetailDTO == null) {
            // 가입 시작
            model.addAttribute("id", userInfo.get("id"));
            model.addAttribute("nickname", userInfo.get("nickname"));
            model.addAttribute("customer", new CustomerDetailDTO());
            return "customer/kakaoUpdate";
        } else {
            return "index";// 나중에 수정
        }
    }

    @PostMapping("/kakaoUpdate")
    public String kakaoUpdate(@ModelAttribute CustomerSaveDTO customerSaveDTO) {
        cs.save(customerSaveDTO);
        return "index";
    }

    @GetMapping("/kakaoLogout")
    public String kakaoLogout() {
        cs.kakaoLogout((String) session.getAttribute("access_token"));
        session.invalidate();
        return "index";
    }

    @GetMapping("/kakaoUnlink")
    public String kakaoUnlink() {
        Long customerNumber = cs.findByEmail((String) session.getAttribute("customerLoginEmail")).getCustomerNumber();
        cs.kakaoUnlink((String) session.getAttribute("access_token"));
        // 그리고 회원 삭제
        as.customerDelete(customerNumber);
        session.invalidate();
        return "index";
    }


    @GetMapping("/basketAddTest")
    public String basketAddTest(Model model) {
        //List<MenuDTO> menuDTOS = ss.menuFindAll(10l);
        List<MenuDTO> menuDTOS = cs.menuFindAll();
        model.addAttribute("menuList", menuDTOS);
        return "store/basketAddTest";
    }

    @GetMapping("/menu")
    @ResponseBody
    public MenuDTO menuDetailAjax(@RequestParam("menuNumber") Long menuNumber) {
        MenuDTO menuDTO = cs.menuDetail(menuNumber);
        System.out.println("menuDTO = " + menuDTO);
        return menuDTO;
    }

    @PostMapping("/basket")
    @ResponseBody
    public String basketAdd(@RequestParam("menuNumber") Long menuNumber, @RequestParam("menuCount") int menuCount) {
        System.out.println("menuNumber = " + menuNumber);
        System.out.println("menuCount = " + menuCount);
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setMenuNumber(menuNumber);
        basketDTO.setMenuCount(menuCount);
        String result = cs.basketAdd(basketDTO);
        System.out.println("result = " + result);
        return result;
    }


}
