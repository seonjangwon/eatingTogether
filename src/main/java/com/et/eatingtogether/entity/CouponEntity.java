package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.system.CouponDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "coupon_table")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_number")
    private Long couponNumber;

    private String couponName;

    private int couponCondition;

    private int couponPrice;

    @OneToOne(mappedBy = "couponEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private MyCouponEntity myCouponEntity;


    public static CouponEntity toCouponSave(CouponDTO couponDTO){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName(couponDTO.getCouponName());
        couponEntity.setCouponCondition(couponDTO.getCouponCondition());
        couponEntity.setCouponPrice(couponDTO.getCouponPrice());
        return couponEntity;
    }



}
