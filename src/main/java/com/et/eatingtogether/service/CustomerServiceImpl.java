package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.MyCouponDTO;
import com.et.eatingtogether.dto.customer.PointDTO;
import com.et.eatingtogether.dto.customer.WishlistDTO;
import com.et.eatingtogether.dto.review.ReplyDetailDTO;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.CustomerRepository;
import com.et.eatingtogether.repository.ReplyRepository;
import com.et.eatingtogether.repository.ReviewFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository cr;
    private final ReviewFileRepository rfr;
    private final ReplyRepository rpr;
    private final HttpSession session;

    @Override
    public void login(CustomerDetailDTO customerDetailDTO) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail(customerDetailDTO.getCustomerEmail());
        if(customerEntity.isPresent()){ // 있으면
            if(customerEntity.get().getCustomerPassword().equals(customerDetailDTO.getCustomerPassword())){ // 일치하면
                System.out.println("로그인");
                session.setAttribute("customerLoginEmail",customerEntity.get().getCustomerEmail());
            } else { // 틀리면
                System.out.println("비밀번호");
                throw new IllegalStateException("이메일 또는 비밀번호가 틀립니다");
            }
        } else { // 없으면
            System.out.println("이메일");
            throw new IllegalStateException("이메일 또는 비밀번호가 틀립니다");
        }

    }

    @Override
    public CustomerDetailDTO findByEmail(String customerLoginEmail) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail(customerLoginEmail);
        return CustomerDetailDTO.toEntity(customerEntity.get());
    }

    @Override
    public String update(CustomerDetailDTO customerDetailDTO) {
        Optional<CustomerEntity> customerEntity = cr.findById(customerDetailDTO.getCustomerNumber());
        if (customerEntity.get().getCustomerPassword().equals(customerDetailDTO.getCustomerPassword())){
            // 비밀 번호가 일치하면
            cr.save(CustomerEntity.toUpdate(customerDetailDTO));
            return "ok";
        } else {
            // 비밀 번호가 일치하지 않으면
            return "no";
        }
    }

    @Override
    public List<OrderDTO> orderList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<OrderEntity> orderEntityList = customerEntity.get().getOrderEntityList();

        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity e : orderEntityList){
            orderDTOList.add(OrderDTO.toEntity(e));
        }
        return orderDTOList;
    }

    @Override
    public OrderDTO findOrder(Long orderNumber) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<OrderEntity> orderEntityList = customerEntity.get().getOrderEntityList();
        for(OrderEntity o : orderEntityList) {
            if(o.getOrderNumber().equals(orderNumber)){
                return OrderDTO.toEntity(o);
            }
        }
        return null;
    }

    @Override
    public List<OrderMenuDTO> orderMenu(Long orderNumber) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<OrderEntity> orderEntityList = customerEntity.get().getOrderEntityList();
        for(OrderEntity o : orderEntityList) {
            if(o.getOrderNumber().equals(orderNumber)){
                List<OrderMenuEntity> orderMenuEntityList = o.getOrderMenuEntityList();
                List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
                for(OrderMenuEntity m : orderMenuEntityList){
                    orderMenuDTOList.add(OrderMenuDTO.toEntity(m));
                }
                return orderMenuDTOList;
            }
        }
        return null;
    }

    @Override
    public CustomerDetailDTO findById(Long customerNumber) {
        return CustomerDetailDTO.toEntity(cr.findById(customerNumber).get());
    }

    @Override
    public List<MyCouponDTO> couponList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<MyCouponDTO> couponDTOList = new ArrayList<>();
        for (MyCouponEntity m : customerEntity.get().getMyCouponEntityList()){
            couponDTOList.add(MyCouponDTO.toEntity(m));
        }
        return couponDTOList;
    }

    @Override
    public List<PointDTO> pointList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<PointDTO> pointDTOList = new ArrayList<>();
        for (PointEntity p : customerEntity.get().getPointEntityList()){
            pointDTOList.add(PointDTO.toEntity(p));
        }
        return pointDTOList;
    }

    @Override
    public List<ReviewDetailDTO> reviewList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<ReviewDetailDTO> reviewDetailDTOList = new ArrayList<>();
        for (ReviewEntity r : customerEntity.get().getReviewEntityList()){
            reviewDetailDTOList.add(ReviewDetailDTO.toEntity(r));
        }
        for (ReviewDetailDTO r : reviewDetailDTOList){
            r.setReplyDetailDTO(ReplyDetailDTO.toEntity(rpr.findById(r.getReviewNumber()).get()));
            for (ReviewFileEntity rf : r.getReviewFileEntityList()){
                r.getReviewFileDTOList().add(ReviewFileDTO.toEntity(rf));
            }
        }
        return reviewDetailDTOList;
    }

    @Override
    public List<WishlistDTO> wishlist() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<WishlistDTO> wishlistDTOList = new ArrayList<>();
        for (WishlistEntity w : customerEntity.get().getWishlistEntity()){
            wishlistDTOList.add(WishlistDTO.toEntity(w));
        }
        return wishlistDTOList;
    }
}
