package com.et.eatingtogether.dto.system;

import com.et.eatingtogether.entity.CouponEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class CouponDTO {
    private Long couponNumber;
    private String couponName;
    private int couponCondition;
    private int couponPrice;

    public static CouponDTO couponList(CouponEntity couponEntity){
        CouponDTO couponDTO = new CouponDTO();
        couponDTO.setCouponNumber(couponEntity.getCouponNumber());
        couponDTO.setCouponName(couponEntity.getCouponName());
        couponDTO.setCouponCondition(couponEntity.getCouponCondition());
        couponDTO.setCouponPrice(couponEntity.getCouponPrice());
        return couponDTO;
    }
}
