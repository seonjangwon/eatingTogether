package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.customer.PointDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "point_table")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_number")
    private Long pointNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number")
    private OrderEntity orderEntity;

    private int pointQuantity;
    private String pointHistory; // 사용/적립
    private LocalDateTime pointTime;

    public static PointEntity toDTO(PointDTO pointDTO,CustomerEntity customerEntity,OrderEntity orderEntity){
        PointEntity pointEntity = new PointEntity();
        pointEntity.setCustomerEntity(customerEntity);
        pointEntity.setOrderEntity(orderEntity);
        pointEntity.setPointQuantity(pointDTO.getPointQuantity());
        pointEntity.setPointHistory(pointDTO.getPointHistory());
        pointEntity.setPointTime(pointDTO.getPointTime());
        return pointEntity;
    }
}
