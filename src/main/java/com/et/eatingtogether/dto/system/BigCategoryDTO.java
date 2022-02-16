package com.et.eatingtogether.dto.system;

import com.et.eatingtogether.entity.BigCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigCategoryDTO {
    private Long bigCategoryNumber;
    private String bigCategoryName;

    public static BigCategoryDTO toDetailTest(BigCategoryEntity bigCategoryEntity)  {
        BigCategoryDTO bigCategoryDTO = new BigCategoryDTO();

        bigCategoryDTO.setBigCategoryNumber(bigCategoryEntity.getBigCategoryNumber());
        bigCategoryDTO.setBigCategoryName(bigCategoryEntity.getBigCategoryName());
        return bigCategoryDTO;
    }

    //지원
    public static BigCategoryDTO toBCDetailDTO(BigCategoryEntity bigCategoryEntity) {
        BigCategoryDTO bigCategoryDTO = new BigCategoryDTO();
        bigCategoryDTO.setBigCategoryNumber(bigCategoryEntity.getBigCategoryNumber());
        bigCategoryDTO.setBigCategoryName(bigCategoryEntity.getBigCategoryName());
        return bigCategoryDTO;
    }
}
