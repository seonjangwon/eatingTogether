package com.et.eatingtogether.dto.review;

import com.et.eatingtogether.entity.ReviewFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFileDTO {
    private Long reviewFileNumber;
    private Long reviewNumber;
    private String reviewFilename;

    public static ReviewFileDTO toEntity(ReviewFileEntity reviewFileEntity){
        ReviewFileDTO reviewFileDTO = new ReviewFileDTO();
        reviewFileDTO.setReviewFileNumber(reviewFileEntity.getReviewFileNumber());
        reviewFileDTO.setReviewNumber(reviewFileEntity.getReviewEntity().getReviewNumber());
        reviewFileDTO.setReviewFilename(reviewFileDTO.getReviewFilename());
        return reviewFileDTO;
    }
}
