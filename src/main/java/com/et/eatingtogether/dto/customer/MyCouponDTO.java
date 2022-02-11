package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.entity.MyCouponEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCouponDTO {
    private Long myCouponNumber;
    private Long customerNumber;
    private String couponName;
    private int couponCondition;
    private int couponPrice;

    public static MyCouponDTO toEntity(MyCouponEntity myCouponEntity){
        MyCouponDTO myCouponDTO = new MyCouponDTO();
        myCouponDTO.setMyCouponNumber(myCouponEntity.getMyCouponNumber());
        myCouponDTO.setMyCouponNumber(myCouponEntity.getMyCouponNumber());
        myCouponDTO.setCouponName(myCouponEntity.getCouponEntity().getCouponName());
        myCouponDTO.setCouponCondition(myCouponEntity.getCouponEntity().getCouponCondition());
        myCouponDTO.setCouponPrice(myCouponEntity.getCouponEntity().getCouponPrice());
        return myCouponDTO;
    }
}
