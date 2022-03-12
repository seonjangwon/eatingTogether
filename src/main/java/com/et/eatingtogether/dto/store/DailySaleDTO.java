package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.DailySaleEntity;
import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySaleDTO {
    private Long dailySaleNumber;
    private LocalDate dailySaleTime;
    private int dailySalePrice;
    private Long storeNumber;


    public static DailySaleDTO toDailySaleDTO (DailySaleEntity dailySaleEntity)    {
        DailySaleDTO dailySaleDTO = new DailySaleDTO();
        dailySaleDTO.setStoreNumber(dailySaleEntity.getStoreEntity().getStoreNumber());
        dailySaleDTO.setDailySaleNumber(dailySaleEntity.getDailySaleNumber());
        dailySaleDTO.setDailySaleTime(dailySaleEntity.getDailySaleTime());
        dailySaleDTO.setDailySalePrice(dailySaleEntity.getDailySalePrice());
        return dailySaleDTO;
    }
}
