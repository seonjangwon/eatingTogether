package com.et.eatingtogether.entity;

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
    private Long storeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigCategory_number")
    private BigCategoryEntity bigCategoryEntity;

    private String storeEmail;
    private String storePassword;

    private String storeFilename;
    private String storeName;
    private String storePhone;
    private String storeOpen;
    private String storeClose;
    private String storeAddress;
//    private String storeBounds; // 배달 가능 범위    아 이거 따로 테이블 만듬
    private int storeWish;

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DailySaleEntity> dailySaleEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DeliveryEntity> deliveryEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<StoreCategoryEntity> storeCategoryEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<MenuEntity> menuEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ReviewEntity> reviewEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "storeEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private StoreBlacklistEntity storeBlacklistEntity;
}
