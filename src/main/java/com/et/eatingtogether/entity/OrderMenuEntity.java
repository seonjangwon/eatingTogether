package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.customer.BasketDTO;
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

    @ManyToOne(fetch = FetchType.LAZY) //ManyToOne으로 변경하세용^^
    @JoinColumn(name = "menu_number")
    private MenuEntity menuEntity;

    private int orderMenuCount;

    // 장원
    // 주문 메뉴 저장용
    public static OrderMenuEntity toDTO(BasketEntity basketEntity, OrderEntity orderEntity){
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrderEntity(orderEntity);
        orderMenuEntity.setMenuEntity(basketEntity.getMenuEntity());
        orderMenuEntity.setOrderMenuCount(basketEntity.getBasketMenuCount());
        return orderMenuEntity;
    }
}
