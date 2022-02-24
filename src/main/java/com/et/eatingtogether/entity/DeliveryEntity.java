package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.store.DeliveryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "delivery_table")
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_number")
    private Long deliveryNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;

    private String deliveryDname;
    private int deliveryTime;
    private int deliveryPrice;


    public static DeliveryEntity toSaveDeliveryEntity (DeliveryDTO deliveryDTO, StoreEntity storeEntity) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setStoreEntity(storeEntity);
        deliveryEntity.setDeliveryDname(deliveryDTO.getDeliveryDname());
        deliveryEntity.setDeliveryTime(deliveryDTO.getDeliveryTime());
        deliveryEntity.setDeliveryPrice(deliveryDTO.getDeliveryPrice());
        return  deliveryEntity;

    }
}
