package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.StoreEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long deliveryNumber;
    private StoreEntity storeEntity;

    private String deliveryDname; //동 이름인가
    private int deliveryTime;   //소요시간
    private int deliveryPrice;  //배달금액



}
