package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.review.ReplySaveDTO;
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
//    @JoinColumn(name = "storeEntity;tore_number")
//    private StoreEntity s

    private String replyContents;


    public static ReplyEntity toDTO(ReplySaveDTO replySaveDTO, ReviewEntity reviewEntity){
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setReplyContents(replySaveDTO.getReplyContents());
        replyEntity.setReviewEntity(reviewEntity);
        return replyEntity;
    }
}
