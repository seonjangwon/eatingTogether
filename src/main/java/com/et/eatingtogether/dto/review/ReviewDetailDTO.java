package com.et.eatingtogether.dto.review;

import com.et.eatingtogether.entity.ReplyEntity;
import com.et.eatingtogether.entity.ReviewEntity;
import com.et.eatingtogether.entity.ReviewFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailDTO {
    private Long reviewNumber;
    private Long storeNumber;
    private String storeName;

    private Long customerNumber;
    private String customerName;

    private String menuName;

    private String reviewComments;
    private int reviewScore;
    private LocalDateTime reviewTime;

    private List<Long> reviewFileListNumber;
    private List<ReviewFileDTO> reviewFileDTOList;
    private List<ReviewFileEntity> reviewFileEntityList;
    private Long replyNumber;
    private ReplyDetailDTO replyDetailDTO;

    public static ReviewDetailDTO toEntity(ReviewEntity reviewEntity){
                                           //List<ReviewFileDTO> reviewFileDTOList,
                                           //ReplyDetailDTO replyDetailDTO){
        ReviewDetailDTO reviewDetailDTO = new ReviewDetailDTO();
        reviewDetailDTO.setReviewNumber(reviewEntity.getReviewNumber());
        reviewDetailDTO.setStoreNumber(reviewEntity.getStoreEntity().getStoreNumber());
        reviewDetailDTO.setStoreName(reviewEntity.getStoreEntity().getStoreName());
        reviewDetailDTO.setCustomerNumber(reviewEntity.getCustomerEntity().getCustomerNumber());
        reviewDetailDTO.setCustomerName(reviewEntity.getCustomerEntity().getCustomerNickname());
        reviewDetailDTO.setMenuName(reviewEntity.getMenuName());
        reviewDetailDTO.setReviewComments(reviewEntity.getReviewContents());
        reviewDetailDTO.setReviewScore(reviewEntity.getReviewScore());
        if (reviewEntity.getReplyEntity() != null) {
            reviewDetailDTO.setReplyNumber(reviewEntity.getReplyEntity().getReplyNumber());
        }
        reviewDetailDTO.setReviewFileEntityList(reviewEntity.getReviewFileEntityList());
        reviewDetailDTO.setReviewTime(reviewEntity.getCreateTime());
        //reviewDetailDTO.setReviewFileListNumber();
        //reviewDetailDTO.setReviewFileDTOList(reviewFileDTOList);
        //reviewDetailDTO.setReplyDetailDTO(replyDetailDTO);
        return reviewDetailDTO;
    }

    // 테스트용
    public static ReviewDetailDTO toEntity1(ReviewEntity reviewEntity){
        ReviewDetailDTO reviewDetailDTO = new ReviewDetailDTO();
        reviewDetailDTO.setReviewNumber(reviewEntity.getReviewNumber());
        reviewDetailDTO.setMenuName(reviewEntity.getMenuName());
        reviewDetailDTO.setReviewComments(reviewEntity.getReviewContents());
        reviewDetailDTO.setReviewScore(reviewEntity.getReviewScore());
//        reviewDetailDTO.setReplyNumber(reviewEntity.getReplyEntity().getReplyNumber());
        reviewDetailDTO.setReviewFileEntityList(reviewEntity.getReviewFileEntityList());
        reviewDetailDTO.setReviewTime(reviewEntity.getCreateTime());

//        reviewDetailDTO.setReviewFileListNumber();
        //reviewDetailDTO.setReviewFileDTOList(reviewFileDTOList);
        //reviewDetailDTO.setReplyDetailDTO(replyDetailDTO);
        return reviewDetailDTO;
    }


}
