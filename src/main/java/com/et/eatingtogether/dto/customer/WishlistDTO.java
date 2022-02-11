package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.entity.WishlistEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {
    private Long wishlistNumber;

    private Long customerNumber;
    private String customerName;

    private Long storeNumber;
    private String storeName;
    private String storeFilename;

    public static WishlistDTO toEntity(WishlistEntity wishlistEntity){
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setWishlistNumber(wishlistEntity.getWishlistNumber());
        wishlistDTO.setCustomerNumber(wishlistEntity.getCustomerEntity().getCustomerNumber());
        wishlistDTO.setCustomerName(wishlistEntity.getCustomerEntity().getCustomerNickname());
        wishlistDTO.setStoreNumber(wishlistEntity.getStoreEntity().getStoreNumber());
        wishlistDTO.setStoreName(wishlistEntity.getStoreEntity().getStoreName());
        wishlistDTO.setStoreFilename(wishlistEntity.getStoreEntity().getStoreFilename());
        return wishlistDTO;
    }
}
