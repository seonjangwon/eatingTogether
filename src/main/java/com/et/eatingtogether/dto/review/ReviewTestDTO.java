package com.et.eatingtogether.dto.review;

import com.et.eatingtogether.entity.ReviewEntity;
import com.et.eatingtogether.entity.ReviewFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTestDTO {
    private Long reviewNumber;
    private String reviewContents;
    private int reviewScore; // 별점
    private MultipartFile reviewFile; // 파일
    private List<ReviewFileDTO> reviewFileDTOList;
    private List<ReviewFileEntity> reviewFileEntityList;


    public static ReviewTestDTO toEntity(ReviewEntity reviewEntity){
        ReviewTestDTO reviewTestDTO = new ReviewTestDTO();
        reviewTestDTO.setReviewNumber(reviewEntity.getReviewNumber());
        reviewTestDTO.setReviewScore(reviewEntity.getReviewScore());
        reviewTestDTO.setReviewContents(reviewEntity.getReviewContents());
        reviewTestDTO.setReviewFileEntityList(reviewEntity.getReviewFileEntityList());

        return reviewTestDTO;
    }

}
