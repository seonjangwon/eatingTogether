package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.RiderDTO;
import com.et.eatingtogether.entity.CouponEntity;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.RiderEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.CouponRepository;
import com.et.eatingtogether.repository.CustomerRepository;
import com.et.eatingtogether.repository.RiderRepository;
import com.et.eatingtogether.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CouponRepository cr; // 쿠폰
    private final RiderRepository rr; // 라이더

    private final CustomerRepository ctr; // 고객
    private final StoreRepository sr; // 업체

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
        for(StoreEntity s : storeEntityList){
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



}
