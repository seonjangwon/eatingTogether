package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.*;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;

import java.util.HashMap;
import java.util.List;

import com.et.eatingtogether.dto.customer.CustomerSaveDTO;

public interface CustomerService {
    void login(CustomerDetailDTO customerDetailDTO);

    CustomerDetailDTO findByEmail(String customerLoginEmail);

    String update(CustomerDetailDTO customerDetailDTO);

    List<OrderDTO> orderList();

    OrderDTO findOrder(Long orderNumber);

    List<OrderMenuDTO> orderMenu(Long orderNumber);

    CustomerDetailDTO findById(Long customerNumber);

    List<MyCouponDTO> couponList();

    List<PointDTO> pointList();

    List<ReviewDetailDTO> reviewList();

    List<WishlistDTO> wishlist();

    List<BasketDTO> basketList();

    int deliveryPrice(Long storeNumber);

    String menuUpDown(Long basketNumber, String type);

    void menuDelete(Long basketNumber);

    Long save(CustomerSaveDTO customerSaveDTO);

    String findByCustomerEmail(String customerEmail);

    void pointUse(int pointUse,Long orderNumber);

    void pointAdd(Long orderNumber);

    Long orderSave(OrderDTO orderDTO);

    void basketDeleteAll();

    String getAccessToken(String code);

    HashMap<String, String> getUserInfo(String access_token);

    void kakaoLogout(String access_token);

    void kakaoUnlink(String access_token);

    MenuDTO menuDetail(Long menuNumber);

    String basketAdd(BasketDTO basketDTO);

    List<MenuDTO> menuFindAll();
}
