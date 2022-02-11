package com.et.eatingtogether.dto.review;

import com.et.eatingtogether.entity.ReviewFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSaveDTO {
    // 이걸로 받는거는 한번에 받아서 컨트롤러 또는 서비스에서 나눠서 저장
    private Long reviewNumber;
    private Long storeNumber;
    private Long customerNumber;
//    private Long orderNumber;
//    private String menuName;
    private String reviewContents;
    private String reviewScore; // 별점
    private List<ReviewFileDTO> reviewFileDTOList;


//    private List<Long> reviewFileListNumber;



}
