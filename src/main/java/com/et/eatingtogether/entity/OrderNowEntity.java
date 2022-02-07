package com.et.eatingtogether.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orderNow_table")
public class OrderNowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderNow_number")
    private Long orderNowNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number")
    private OrderEntity orderEntity;

    private String orderNowStatus;
    private LocalDateTime orderNowTime; // 음 뭐지 시간? 현재 시간 +00분 이런식으로 해야하나?
}
