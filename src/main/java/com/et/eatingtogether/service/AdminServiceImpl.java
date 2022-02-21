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
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CouponRepository cr; // 쿠폰
    private final RiderRepository rr; // 라이더

    private final CustomerRepository ctr; // 고객
    private final StoreRepository sr; // 업체

    private final ReviewFileRepository rfr; // 리뷰파일
    private final ReviewRepository rer; // 리뷰

    private final CustomerBlacklistRepository cbr; // 회원블랙리스트
    private final CustomerReportRepository crr; // 회원신고내역

    // 쿠폰저장
    @Override
    public void save(CouponDTO couponDTO) {
        cr.save(CouponEntity.toCouponSave(couponDTO));
    }

    @Override
    public List<CouponDTO> findAll() {
        List<CouponEntity> couponEntityList = cr.findAll();
        List<CouponDTO> couponList = new ArrayList<>();
        for (CouponEntity c : couponEntityList) {
            couponList.add(CouponDTO.couponList(c));
        }

        return couponList;
    }

    // 라이더 저장
    @Override
    public void riderSave(RiderDTO riderDTO) {
        rr.save(RiderEntity.toRiderSave(riderDTO));
    }


    // 라이더리스트 출력
    @Override
    public List<RiderDTO> riderFindAll() {
        List<RiderEntity> riderEntityList = rr.findAll();
        List<RiderDTO> riderList = new ArrayList<>();
        for (RiderEntity r : riderEntityList) {
            riderList.add(RiderDTO.riderDTOList(r));
        }
        return riderList;
    }

    // 회원리스트 출력
    @Override
    public List<CustomerDetailDTO> CustomerFindAll() {
        List<CustomerEntity> customerEntityList = ctr.findAll();
        List<CustomerDetailDTO> customerList = new ArrayList<>();
        for (CustomerEntity c : customerEntityList) {
            customerList.add(CustomerDetailDTO.toEntity(c));
        }

        return customerList;
    }

    // 회원삭제
    @Override
    public void customerDelete(Long customerNum) {
        ctr.deleteById(customerNum);
    }

    // 회원삭제 테스트용 findById
    @Override
    public CustomerDetailDTO findById(Long customerNum) {
        CustomerEntity customerEntity = ctr.findById(customerNum).get();
        CustomerDetailDTO customerDetailDTO = CustomerDetailDTO.toEntity(customerEntity);
        return customerDetailDTO;
    }

    // 업체목록조회
    @Override
    public List<StoreDetailDTO> storeFindAll() {
        List<StoreEntity> storeEntityList = sr.findAll();
        List<StoreDetailDTO> storeList = new ArrayList<>();
        for (StoreEntity s : storeEntityList) {
            storeList.add(StoreDetailDTO.toStoreDetailDTO(s));
        }
        System.out.println("서비스임플 storeList = " + storeList);
        return storeList;
    }

    // 업체삭제
    @Override
    public void storeDelete(Long storeNum) {
        sr.deleteById(storeNum);
    }

    // 업체 삭제 테스트(를 위한 저장)
    @Override
    public Long storeSave(StoreSaveDTO storeSaveDTO) {
        return sr.save(StoreEntity.toStoreSave(storeSaveDTO)).getStoreNumber();
    }

    // 고객리뷰 저장
    @Override
    public void reviewSave(ReviewSaveDTO reviewSaveDTO) throws IOException {
        if(reviewSaveDTO!=null){
            //      reviewSaveDTO.getReviewFileDTOList().forEach(reviewFileDTO ->{
//          rfr.save(reviewFileDTO.getReviewFilename());
//      });
            // 1. 리뷰Entity에 saveDTO 저장
            // 2. 리뷰 Entity
            Long reviewNumber = rer.save(ReviewEntity.toReviewSave(reviewSaveDTO)).getReviewNumber();
            System.out.println("reviewNumber = " + reviewNumber);
            ReviewEntity reviewEntity = rer.findById(reviewNumber).get();
//        ReviewTestDTO reviewTestDTO = ReviewTestDTO.toEntity(reviewEntity);
//        System.out.println("reviewTestDTO = " + reviewTestDTO);
//        System.out.println("reviewEntity = " + reviewEntity);

            // r : reviewSaveDTO에 있는 파일리스트부분, reviewEntity : reviewRepository에 저장한 작성된 리뷰

            if (!reviewSaveDTO.getReviewFileDTOList().isEmpty()) {
                for (ReviewFileDTO r : reviewSaveDTO.getReviewFileDTOList()) {

                    MultipartFile r_file = r.getReviewFile();
                    String r_fileName = System.currentTimeMillis() + r_file.getOriginalFilename();
                    System.out.println("r_fileName = " + r_fileName);
                    // 저장경로
                    String savePath = "C:\\development_psy\\source\\springboot\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName;

                    // 만약 r_file이 비어있지 않다면 저장경로에 저장하기
                    if (r_file != null) {
                        r_file.transferTo(new File(savePath));
                    }
                    // 파일이름 dto에 저장
                    r.setReviewFilename(r_fileName);

                    rfr.save(ReviewFileEntity.toEntity(r, reviewEntity));

                }
            }

        }

    }


    // 리뷰 목록 출력용
    @Override
    public List<ReviewDetailDTO> reviewFindAll() {
        List<ReviewEntity> reviewEntityList = rer.findAll();
        List<ReviewDetailDTO> reviewList = new ArrayList<>();
        for (ReviewEntity r : reviewEntityList) {
            reviewList.add(ReviewDetailDTO.toEntity(r));
        }
        return reviewList;
    }

    @Override
    public void reviewSave1(ReviewFileDTO r,ReviewSaveDTO reviewSaveDTO)throws IOException {
        Long reviewNumber = rer.save(ReviewEntity.toReviewSave(reviewSaveDTO)).getReviewNumber();
        System.out.println("reviewNumber = " + reviewNumber);
        ReviewEntity reviewEntity = rer.findById(reviewNumber).get();
        MultipartFile r_file = r.getReviewFile();
        String r_fileName = System.currentTimeMillis() + r_file.getOriginalFilename();
        System.out.println("r_fileName = " + r_fileName);
        // 저장경로
        String savePath = "C:\\development_psy\\source\\springboot\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName;

        // 만약 r_file이 비어있지 않다면 저장경로에 저장하기
        if (r_file != null) {
            r_file.transferTo(new File(savePath));
        }
        // 파일이름 dto에 저장
        r.setReviewFilename(r_fileName);

        rfr.save(ReviewFileEntity.toEntity(r, reviewEntity));
    }


}
