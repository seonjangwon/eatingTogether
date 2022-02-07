package com.et.eatingtogether.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "reply_table")
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_number")
    private Long replyNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_number")
    private ReviewEntity reviewEntity;

    // 이게 필요 한가? 리뷰번호가 있는데?
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_number")
//    private StoreEntity storeEntity;

    private String replyContents;
}