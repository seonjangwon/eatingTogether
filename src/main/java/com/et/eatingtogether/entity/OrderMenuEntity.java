package com.et.eatingtogether.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orderMenu_table")
public class OrderMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderMenu_number")
    private Long orderMenuNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_number")
    private OrderEntity orderEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_number")
    private MenuEntity menuEntity;

    private int orderMenuCount;

}
