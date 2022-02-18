package com.et.eatingtogether.dto.review;

import com.et.eatingtogether.entity.ReviewFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewFileDTO {
    private Long reviewFileNumber; // 리뷰파일번호
    private Long reviewNumber; // 리뷰번호(참조)
    private MultipartFile reviewFile; // 파일
    private String reviewFilename; // 파일명


    public static ReviewFileDTO toEntity (ReviewFileEntity reviewFileEntity){
        ReviewFileDTO reviewFileDTO = new ReviewFileDTO();
        reviewFileDTO.setReviewFileNumber(reviewFileEntity.getReviewFileNumber());
        reviewFileDTO.setReviewNumber(reviewFileEntity.getReviewEntity().getReviewNumber());
        reviewFileDTO.setReviewFilename(reviewFileEntity.getReviewFilename());
        return reviewFileDTO;
    }
}
