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
    private Long reviewNumber; // 리뷰번호
    private Long storeNumber; // 업체번호
    private String storeName; // 업체이름

    private Long customerNumber; // 회원번호
    private String customerName; // 회원이름

    private String menuName; // 메뉴이름

    private String reviewComments; // 리뷰내용
    private int reviewScore; // 리뷰평점
    private LocalDateTime reviewTime; // 리뷰작성 시간

    private List<Long> reviewFileListNumber; // 파일리스트번호
    private List<ReviewFileDTO> reviewFileDTOList; // 파일DTO 리스트
    private List<ReviewFileEntity> reviewFileEntityList; // 파일ENTITY 리스트
    private Long replyNumber; // 답글번호
    private ReplyDetailDTO replyDetailDTO; // 답글정보가 담긴 DTO

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
        if (reviewEntity.getReplyEntity() != null) { // 만약 reviewEntity에 있는 replyEntity 가 null이 아닐경우 replyNumber 저장
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
