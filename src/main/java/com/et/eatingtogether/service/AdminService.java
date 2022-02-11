package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.RiderDTO;
import com.et.eatingtogether.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    void save(CouponDTO couponDTO);

    List<CouponDTO> findAll();

    void riderSave(RiderDTO riderDTO);

    List<RiderDTO> riderFindAll();

    List<CustomerDetailDTO> CustomerFindAll();


    void customerDelete(Long customerNum);

    // 회원삭제 테스트용 findById
    CustomerDetailDTO findById(Long customerNum);

    List<StoreDetailDTO> storeFindAll();

    void storeDelete(Long storeNum);

    Long storeSave(StoreSaveDTO storeSaveDTO);
}
