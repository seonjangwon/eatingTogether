package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.DailySaleEntity;
import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySaleDTO {
    private Long dailySaleNumber;
    private LocalDateTime dailySaleTime;
    private int dailySalePrice;


    public static DailySaleDTO toDailySaleDTO (DailySaleEntity dailySaleEntity)    {
        DailySaleDTO dailySaleDTO = new DailySaleDTO();
        dailySaleDTO.setDailySaleNumber(dailySaleEntity.getDailySaleNumber());
        dailySaleDTO.setDailySaleTime(dailySaleEntity.getDailySaleTime());
        dailySaleDTO.setDailySalePrice(dailySaleEntity.getDailySalePrice());
        return dailySaleDTO;
    }
}
