package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.review.ReviewSaveDTO;
import com.et.eatingtogether.dto.review.ReviewTestDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.RiderDTO;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.ReviewEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    // 쿠폰 저장
    void save(CouponDTO couponDTO);

    // 쿠폰 전체목록 조회
    List<CouponDTO> findAll();

    // 라이더 등록
    void riderSave(RiderDTO riderDTO);

    // 라이더 전체목록 조회
    List<RiderDTO> riderFindAll();

    // 회원 전체목록 조회
    List<CustomerDetailDTO> CustomerFindAll();


    // 회원삭제
    void customerDelete(Long customerNum);

    // 회원삭제 테스트용 findById
    CustomerDetailDTO findById(Long customerNum);

    // 업체 전체목록 조회
    List<StoreDetailDTO> storeFindAll();

    // 업체삭제
    void storeDelete(Long storeNum);

    // 업체등록
    Long storeSave(StoreSaveDTO storeSaveDTO);

    // 리뷰등록
    void reviewSave(ReviewSaveDTO reviewSaveDTO) throws IOException;

    // 리뷰 목록 출력용
    List<ReviewTestDTO> reviewFindAll();


    void reviewSave1(ReviewFileDTO r,ReviewSaveDTO reviewSaveDTO)throws IOException;
}
