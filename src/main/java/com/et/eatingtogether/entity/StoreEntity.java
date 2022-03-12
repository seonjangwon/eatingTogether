package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreLoginDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "store_table")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_number")
    private Long storeNumber; // 업체번호(pk)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigCategory_number") // join하는 컬럼은 카테고리 번호( pk값을 참조한다)
    private BigCategoryEntity bigCategoryEntity; // 한식, 중식 등 메뉴 카테고리

    private String storeEmail; //  업체 이메일(회원가입 시 작성하는)
    private String storePassword; // 업체 비밀번호

    private String storeFilename; // 업체 파일이름(업체 등록시 저장하는 이미지파일)
    private String storeName; // 업체 이름
    private String storePhone; // 업체 전화번호
    private String storeOpen; // 업체 오픈시간
    private String storeClose; // 업체 마감시간
    private String storeAddress; // 업체 주소
//    private String storeBounds; // 배달 가능 범위    아 이거 따로 테이블 만듬
    private int storeWish; // 업체 찜?

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DailySaleEntity> dailySaleEntityList = new ArrayList<>(); // 당일 매출액

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DeliveryEntity> deliveryEntityList = new ArrayList<>(); // 배송지, 배달비 관련 목록

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<StoreCategoryEntity> storeCategoryEntityList = new ArrayList<>(); // 업체에서 정한 메뉴 카테고리 목록

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<MenuEntity> menuEntityList = new ArrayList<>(); // 메뉴 목록

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>(); // 주문목록

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>(); // 리뷰목록

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<StoreBlacklistEntity> storeBlacklistEntityList = new ArrayList<>(); // 신고 얼마나 당했는지 (업체가 신고당한 횟수가 많을수도 있으므로 1:N 관계)


    public static StoreEntity toSaveStore(StoreSaveDTO storeSaveDTO,  BigCategoryEntity bigCategoryEntity) {
        StoreEntity storeEntity = new StoreEntity();

        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreEmail(storeSaveDTO.getStoreEmail());
        storeEntity.setStorePassword(storeSaveDTO.getStorePassword());
        storeEntity.setStoreFilename(storeSaveDTO.getStoreFilename());
        storeEntity.setStoreName(storeSaveDTO.getStoreName());
        storeEntity.setStorePhone(storeSaveDTO.getStorePhone());
        storeEntity.setStoreOpen(storeSaveDTO.getStoreOpen());
        storeEntity.setStoreClose(storeSaveDTO.getStoreClose());
        storeEntity.setStoreAddress(storeSaveDTO.getStoreAddress());

        return storeEntity;

    }

    public static StoreEntity toStoreSave(StoreSaveDTO storeSaveDTO){
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreName(storeSaveDTO.getStoreName());
        return storeEntity;
    }

    public static StoreEntity toUpdate(StoreDetailDTO storeDetailDTO, BigCategoryEntity bigCategoryEntity) {
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreEmail(storeDetailDTO.getStoreEmail());
        storeEntity.setStoreNumber(storeDetailDTO.getStoreNumber());
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreName(storeDetailDTO.getStoreName());
        storeEntity.setStorePassword(storeDetailDTO.getStorePassword());
        storeEntity.setStorePhone(storeDetailDTO.getStorePhone());
        storeEntity.setStoreOpen(storeDetailDTO.getStoreOpen());
        storeEntity.setStoreClose(storeDetailDTO.getStoreClose());
        storeEntity.setStoreAddress(storeDetailDTO.getStoreAddress());
        return storeEntity;
    }

}
