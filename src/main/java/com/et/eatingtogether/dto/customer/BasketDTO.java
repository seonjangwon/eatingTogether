package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.entity.BasketEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {
    private Long basketNumber;

    private Long customerNumber;
    private String customerName;

    private Long storeNumber;
    private String storeName;

    private Long menuNumber;
    private String menuName;
    private String menuFilename;
    private int menuPrice;
    private int menuCount;

    public static BasketDTO toEntity(BasketEntity basketEntity){
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBasketNumber(basketEntity.getBasketNumber());
        basketDTO.setCustomerNumber(basketEntity.getCustomerEntity().getCustomerNumber());
        basketDTO.setCustomerName(basketEntity.getCustomerEntity().getCustomerNickname());
        basketDTO.setStoreNumber(basketEntity.getStoreEntity().getStoreNumber());
        basketDTO.setStoreName(basketEntity.getStoreEntity().getStoreName());
        basketDTO.setMenuNumber(basketEntity.getMenuEntity().getMenuNumber());
        basketDTO.setMenuName(basketEntity.getMenuEntity().getMenuName());
        basketDTO.setMenuFilename(basketEntity.getMenuEntity().getMenuFilename());
        basketDTO.setMenuPrice(basketEntity.getMenuEntity().getMenuPrice());
        basketDTO.setMenuCount(basketEntity.getBasketMenuCount());
        return basketDTO;
    }
}
