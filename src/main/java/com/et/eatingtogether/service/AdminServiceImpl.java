package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerBlacklistDTO;
import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.review.*;
import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.ReportSaveDTO;
import com.et.eatingtogether.dto.system.RiderDTO;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
    private final StoreBlacklistRepository sbr; // 업체블랙리스트
    private final OrderRepository or;

    private final MenuRepository mr; // 메뉴
    private final HttpSession session; // 로그인세션

    private final ReplyRepository rep; // 사장님 답글

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

    // 고객리뷰 저장 220222 선영
    @Override
    public void reviewSave(ReviewSaveDTO reviewSaveDTO) throws IOException {
        if (reviewSaveDTO != null) {
            OrderEntity orderEntity = or.findById(reviewSaveDTO.getOrderNumber()).get();
            Long reviewNumber = rer.save(ReviewEntity.toReviewSave(reviewSaveDTO, orderEntity)).getReviewNumber();
            System.out.println("reviewNumber = " + reviewNumber);

            ReviewEntity reviewEntity = rer.findById(reviewNumber).get();

            // r : reviewSaveDTO에 있는 파일리스트부분, reviewEntity : reviewRepository에 저장한 작성된 리뷰
            if (!reviewSaveDTO.getReviewFileDTOList().isEmpty()) {
                for (ReviewFileDTO r : reviewSaveDTO.getReviewFileDTOList()) {

                    MultipartFile r_file = r.getReviewFile();
                    String r_fileName = System.currentTimeMillis() + r_file.getOriginalFilename();
                    System.out.println("r_fileName = " + r_fileName);
                    // 저장경로
                    /*String savePath = "C:\\development_psy\\source\\springboot\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName;*/
//                    String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName; //지원
//                    String savePath = "C:\\Users\\wkddn\\Desktop\\wkddnjs\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName; //장원
                    String savePath = "C:\\development\\source\\FinalProject\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName; //장원

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
//        // dto -> entity
        // 1. 해당 업체의 리뷰 불러오기 3. 연관된 파일들 불러오기
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail"));
        System.out.println("storeEntity = " + storeEntity);
        List<ReviewEntity> reviewEntityList = storeEntity.getReviewEntityList();
        // entity -> dto로 바꾸기
        List<ReviewDetailDTO> reviewList = new ArrayList<>(); // entity에서 가져온 리스트를 담을 List<DTO> 객체생성

        // for문으로 반복문을 돌려서 entityList에 있는걸 dtoList에 담음
        for (ReviewEntity r : reviewEntityList) {
            reviewList.add(ReviewDetailDTO.toEntity(r));
        }

        // entity 정보가 담긴 dtoList를 for문으로 반복문을 돌려 파일, 사장님 답글 저장하는 걸 처리함
        for (ReviewDetailDTO rd : reviewList) {
            List<ReviewFileDTO> reviewFileDTOS = new ArrayList<>();
            // 답글처리( 주석처리된 부분이랑 아래 부분이랑 같은 코드임)
//           if(rd.getReplyNumber()!=null){
//           Optional<ReplyEntity> optionalReplyEntity = rep.findById(rd.getReplyNumber());
//            rd.setReplyDetailDTO(ReplyDetailDTO.toEntity(optionalReplyEntity.get()));
//              }
            Optional<ReplyEntity> optionalReplyEntity = null;
            if (rd.getReplyNumber() != null) {
                optionalReplyEntity = rep.findById(rd.getReplyNumber());
                rd.setReplyDetailDTO(ReplyDetailDTO.toEntity(optionalReplyEntity.get()));
            }

            // 파일
            for (ReviewFileEntity rfe : rd.getReviewFileEntityList()) {
                reviewFileDTOS.add(ReviewFileDTO.toEntity(rfe));
            }

            rd.setReviewFileDTOList(reviewFileDTOS); // 끝

        }
        System.out.println("reviewList = " + reviewList);
        return reviewList;
    }

    @Override
    public void reviewSave1(ReviewFileDTO r, ReviewSaveDTO reviewSaveDTO) throws IOException {
//        // 회원 Entity 필요함
//        CustomerEntity customerEntity = ctr.findById(reviewSaveDTO.getCustomerNumber()).get();
//        // 업체 Entity 필요함
//        StoreEntity storeEntity = sr.findById(reviewSaveDTO.getStoreNumber()).get();
//        // 메뉴이름 필요함
////            String menuName = sr.findById(storeEntity.getStoreNumber()).get().getMenuEntityList().get(Math.toIntExact(reviewSaveDTO.getOrderNumber())).getMenuName();
//        String menuName = storeEntity.getMenuEntityList().get(Math.toIntExact(reviewSaveDTO.getOrderNumber())).getMenuName();
//
//
//        Long reviewNumber = rer.save(ReviewEntity.toReviewSave(reviewSaveDTO)).getReviewNumber();
//        System.out.println("reviewNumber = " + reviewNumber);
//
//        ReviewEntity reviewEntity = rer.findById(reviewNumber).get();
//        MultipartFile r_file = r.getReviewFile();
//        String r_fileName = System.currentTimeMillis() + r_file.getOriginalFilename();
//        System.out.println("r_fileName = " + r_fileName);
//        // 저장경로
//        String savePath = "C:\\development_psy\\source\\springboot\\eatingTogether\\src\\main\\resources\\static\\upload\\review\\" + r_fileName;
//
//        // 만약 r_file이 비어있지 않다면 저장경로에 저장하기
//        if (r_file != null) {
//            r_file.transferTo(new File(savePath));
//        }
//        // 파일이름 dto에 저장
//        r.setReviewFilename(r_fileName);
//
//        rfr.save(ReviewFileEntity.toEntity(r, reviewEntity));
    }

    // 회원신고 저장
    @Override
    public void reportSave(CustomerBlacklistDTO customerBlacklistDTO) {
        //dto -> entity
        CustomerEntity customerEntity = ctr.findById(customerBlacklistDTO.getCustomerNumber()).get();
        CustomerBlacklistEntity customerBlacklistEntity = CustomerBlacklistEntity.toEntity(customerBlacklistDTO, customerEntity);
        System.out.println("customerBlacklistEntity.getReportCause() = " + customerBlacklistEntity.getReportCause());
        cbr.save(customerBlacklistEntity);

    }

    // 블랙리스트 회원
    @Override
    public List<CustomerBlacklistDTO> cblackList() {
        List<CustomerBlacklistEntity> customerBlacklistEntityList = cbr.findAll();
        // ENTITY -> DTO
        List<CustomerBlacklistDTO> customerBlacklist = new ArrayList<>();
        for (CustomerBlacklistEntity c : customerBlacklistEntityList) {
            customerBlacklist.add(CustomerBlacklistDTO.toDTO(c));
        }

        return customerBlacklist;
    }

    // 업체신고 저장
    @Override
    public void storeReportSave(StoreBlacklistDTO storeBlacklistDTO) {
        StoreEntity storeEntity = sr.findById(storeBlacklistDTO.getStoreNumber()).get();
        // dto -> entity
        StoreBlacklistEntity storeBlacklistEntity = StoreBlacklistEntity.toEntity(storeBlacklistDTO, storeEntity);
        sbr.save(storeBlacklistEntity);
    }

    // 사장님 답글 저장처리
    @Override
    public void replySave(ReplySaveDTO replySaveDTO) {
        // dto -> entity
        if (replySaveDTO.getReviewNumber() != null) {
            Optional<ReviewEntity> reviewEntity = rer.findById(replySaveDTO.getReviewNumber());
            ReplyEntity replyEntity = ReplyEntity.toDTO(replySaveDTO, reviewEntity.get());
            rep.save(replyEntity);
        }
    }



}
