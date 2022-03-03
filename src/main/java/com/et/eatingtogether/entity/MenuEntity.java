package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.store.MenuDetailDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    //아래 두 컬럼 FetchType.Lazy로 변경.
    @OneToOne(mappedBy = "menuEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private BasketEntity basketEntity;
    @OneToMany(mappedBy = "menuEntity", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<OrderMenuEntity> orderMenuEntityList = new ArrayList<>();
    //orderMenuEntity는 OneToMany로 변경하세요.

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

    //지원
    public static MenuEntity toUpdateMenuEntity (MenuDetailDTO menuDetailDTO, StoreEntity storeEntity, StoreCategoryEntity storeCategoryEntity) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuNumber(menuDetailDTO.getMenuNumber());
        menuEntity.setMenuName(menuDetailDTO.getMenuName());
        menuEntity.setMenuPrice(menuDetailDTO.getMenuPrice());
        menuEntity.setMenuExplain(menuDetailDTO.getMenuExplain());
        menuEntity.setMenuFilename(menuDetailDTO.getMenuFilename());
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        return menuEntity;
    }
}
