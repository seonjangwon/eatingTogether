package com.et.eatingtogether.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "review_table")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_number")
    private Long reviewNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number")
    private OrderEntity orderEntity;

    private String menuName;// , 단위로 구분해서 출력할 것임
//    private String reviewFilename; // 테이블 따로 뺌
    private String reviewContents;
    private int reviewScore;
    private LocalDateTime reviewTime;

    @OneToMany(mappedBy = "reviewEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ReviewFileEntity> reviewFileEntityList = new ArrayList<>();

    @OneToOne(mappedBy = "reviewEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ReplyEntity replyEntity;
}
