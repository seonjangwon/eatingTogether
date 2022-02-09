package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.entity.PointEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {
    private Long pointNumber;
    private Long customerNumber;
    private Long orderNumber;

    private int pointQuantity;
    private String pointHistory;
    private LocalDateTime pointTime;

    private Long storeNumber;
    private String storeName;

    public static PointDTO toEntity(PointEntity pointEntity){
        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointNumber(pointEntity.getPointNumber());
        pointDTO.setCustomerNumber(pointEntity.getCustomerEntity().getCustomerNumber());
        pointDTO.setOrderNumber(pointEntity.getOrderEntity().getOrderNumber());
        pointDTO.setPointQuantity(pointEntity.getPointQuantity());
        pointDTO.setPointHistory(pointEntity.getPointHistory());
        pointDTO.setPointTime(pointEntity.getPointTime());
        pointDTO.setStoreName(pointEntity.getOrderEntity().getStoreEntity().getStoreName());
        pointDTO.setStoreNumber(pointEntity.getOrderEntity().getStoreEntity().getStoreNumber());
        return pointDTO;
    }
}
