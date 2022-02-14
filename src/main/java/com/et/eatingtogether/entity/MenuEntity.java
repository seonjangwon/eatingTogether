package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.store.MenuDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "menu_table")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_number")
    private Long menuNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_number")
    private StoreEntity storeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeCategory_number")
    private StoreCategoryEntity storeCategoryEntity;

    private String menuName;
    private String menuFilename;
    private int menuPrice;
    private String menuExplain;

    @OneToOne(mappedBy = "menuEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private BasketEntity basketEntity;
    @OneToOne(mappedBy = "menuEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private OrderMenuEntity orderMenuEntity;

    //지원
    public static MenuEntity toSaveMenuEntity(MenuDTO menuDTO, StoreEntity storeEntity, StoreCategoryEntity storeCategoryEntity){
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuName(menuDTO.getMenuName());
        menuEntity.setMenuPrice(menuDTO.getMenuPrice());
        menuEntity.setMenuExplain(menuDTO.getMenuExplain());
        menuEntity.setMenuFilename(menuDTO.getMenuFilename());
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        return menuEntity;
    }
}
