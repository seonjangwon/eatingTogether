package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.customer.CustomerBlacklistDTO;
import com.et.eatingtogether.dto.store.StoreBlacklistDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "storeBlacklist_table")
public class StoreBlacklistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeBlacklist_number")
    private Long storeBlacklistNumber; // pk

    @Column
    private String reportCause; // 신고사유

    @Column
    private String reportOpinion; // 기타의견

    @Column
    private String loginCustomEmail; // 신고하는 회원?


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;


//    private int storeBlacklistCount; // 신고횟수는 시간있으면

    public static StoreBlacklistEntity toEntity (StoreBlacklistDTO storeBlacklistDTO, StoreEntity storeEntity){
        StoreBlacklistEntity storeBlacklistEntity = new StoreBlacklistEntity();
        storeBlacklistEntity.setStoreEntity(storeEntity);
        storeBlacklistEntity.setReportCause(storeBlacklistDTO.getReportCause());
        storeBlacklistEntity.setReportOpinion(storeBlacklistDTO.getReportOpinion());
        storeBlacklistEntity.setLoginCustomEmail(storeBlacklistDTO.getCustomerLoginEmail());
        return storeBlacklistEntity;

    }


}
