package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.system.OrderDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "order_table")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Long orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderMenuEntity> orderMenuEntityList = new ArrayList<>();

    private int orderPrice;
    private LocalDateTime orderTime;
    private String orderType;
    private String orderAddress;
    private String orderTomaster;
    private String orderTorider;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<PointEntity> pointEntityList;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private OrderNowEntity orderNowEntity;

    @OneToOne(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ReviewEntity reviewEntity;

    public static OrderEntity toDTO(OrderDTO orderDTO,CustomerEntity customerEntity,
                                    StoreEntity storeEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerEntity(customerEntity);
        orderEntity.setStoreEntity(storeEntity);
        orderEntity.setOrderPrice(orderDTO.getOrderPrice());
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderType(orderDTO.getOrderType());
        orderEntity.setOrderAddress(orderDTO.getOrderAddress());
        orderEntity.setOrderTomaster(orderDTO.getOrderTomaster());
        orderEntity.setOrderTorider(orderDTO.getOrderTorider());
        return orderEntity;
    }
}
