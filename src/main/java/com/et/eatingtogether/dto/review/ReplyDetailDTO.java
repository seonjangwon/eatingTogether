package com.et.eatingtogether.dto.review;

import com.et.eatingtogether.entity.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDetailDTO {
    private Long replyNumber;
    private Long storeNumber;
    private Long reviewNumber;
    private String replyContents;

    public static ReplyDetailDTO toEntity(ReplyEntity replyEntity){
        ReplyDetailDTO replyDetailDTO = new ReplyDetailDTO();
        replyDetailDTO.setReplyNumber(replyEntity.getReplyNumber());
        replyDetailDTO.setStoreNumber(replyEntity.getReviewEntity().getStoreEntity().getStoreNumber());
        replyDetailDTO.setReviewNumber(replyEntity.getReviewEntity().getReviewNumber());
        replyDetailDTO.setReplyContents(replyEntity.getReplyContents());
        return replyDetailDTO;
    }
}
