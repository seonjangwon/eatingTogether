package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.review.ReviewSaveDTO;
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
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_number")
    private Long reviewNumber; // 리뷰번호(pk)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity; // storeNumber로 storeEntity 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity; // customerNumber로 customerEntity 참조

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number")
    private OrderEntity orderEntity; // orderNumber로 orderEntity 참조

    private String menuName;// 메뉴이름, 단위로 구분해서 출력할 것임
    private int reviewScore; // 리류 평점
    private LocalDateTime createTime; // 리뷰 등록시간
    private String reviewContents; // 리뷰 내용


    //    private LocalDateTime reviewTime;
    //    private String reviewFilename; // 테이블 따로 뺌

    @OneToMany(mappedBy = "reviewEntity",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ReviewFileEntity> reviewFileEntityList = new ArrayList<>(); // 리뷰 하나에 파일 여러개가 있으므로 리뷰 파일리스트 형식으로 매핑

    @OneToOne(mappedBy = "reviewEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ReplyEntity replyEntity; // 리뷰와 답글은 1개씩 달 수 있으므로 리뷰Entity와 답글 Entity는 1:1 매핑

    // 리뷰저장
    // 리뷰를 저장할 때 리뷰저장시 작성한 정보(리뷰내용, 이미지파일 등)와 주문정보가 담긴 orderEntity를 함께 넘겨줘야 한다.
    public static ReviewEntity toReviewSave(ReviewSaveDTO reviewSaveDTO, OrderEntity orderEntity){
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReviewNumber(reviewSaveDTO.getReviewNumber());
        reviewEntity.setReviewScore(reviewSaveDTO.getReviewScore());
        reviewEntity.setReviewContents(reviewSaveDTO.getReviewContents());
        reviewEntity.setOrderEntity(orderEntity);
        reviewEntity.setStoreEntity(orderEntity.getStoreEntity());
        reviewEntity.setCustomerEntity(orderEntity.getCustomerEntity());
        reviewEntity.setMenuName(orderEntity.getOrderMenuEntityList().get(0).getMenuEntity().getMenuName());
        return reviewEntity;

    }

}
