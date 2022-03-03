package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.customer.BasketDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "basket_table")
public class BasketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_number")
    private Long basketNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_number")
    private MenuEntity menuEntity;

    private int basketMenuCount;

    public static BasketEntity toDTO(BasketDTO basketDTO,CustomerEntity customerEntity,
                                     StoreEntity storeEntity, MenuEntity menuEntity){
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setCustomerEntity(customerEntity);
        basketEntity.setStoreEntity(storeEntity);
        basketEntity.setMenuEntity(menuEntity);
        basketEntity.setBasketMenuCount(basketDTO.getMenuCount());
        return basketEntity;
    }
}
