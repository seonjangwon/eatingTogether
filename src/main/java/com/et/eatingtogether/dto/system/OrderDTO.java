package com.et.eatingtogether.dto.system;

import com.et.eatingtogether.entity.OrderEntity;
import com.et.eatingtogether.entity.OrderNowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderNumber;
    private Long customerNumber;
    private Long storeNumber;
    private int orderPrice;
    private LocalDateTime orderTime;
    private String orderType;
    private String orderAddress;
    private String orderTomaster;
    private String orderTorider;

    private String storeFilename;
    private String storeName;
    private String menuName;
    private boolean review;
    private String orderNow;

    public static OrderDTO toEntity(OrderEntity orderEntity){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber(orderEntity.getOrderNumber());
        orderDTO.setCustomerNumber(orderEntity.getCustomerEntity().getCustomerNumber());
        orderDTO.setStoreNumber(orderEntity.getStoreEntity().getStoreNumber());
        orderDTO.setOrderPrice(orderEntity.getOrderPrice());
        orderDTO.setOrderTime(orderEntity.getOrderTime());
        orderDTO.setOrderType(orderEntity.getOrderType());
        orderDTO.setOrderAddress(orderEntity.getOrderAddress());
        orderDTO.setOrderTomaster(orderEntity.getOrderTomaster());
        orderDTO.setOrderTorider(orderEntity.getOrderTorider());
        orderDTO.setStoreFilename(orderEntity.getStoreEntity().getStoreFilename());
        orderDTO.setMenuName(orderEntity.getOrderMenuEntityList().get(0).getMenuEntity().getMenuName());
        // 리뷰가 없으면 트루
        orderDTO.setReview(orderEntity.getReviewEntity() == null);
        orderDTO.setOrderNow(orderEntity.getOrderNowEntity().getOrderNowStatus());
        orderDTO.setStoreName(orderEntity.getStoreEntity().getStoreName());
        return orderDTO;
    }

    //지원
    public static OrderDTO toStoreOrderDetailDTO (OrderEntity orderEntity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderNumber(orderEntity.getOrderNumber());
        orderDTO.setCustomerNumber(orderEntity.getCustomerEntity().getCustomerNumber());
        orderDTO.setStoreNumber(orderEntity.getStoreEntity().getStoreNumber());
        orderDTO.setOrderPrice(orderEntity.getOrderPrice());
        orderDTO.setOrderTime(orderEntity.getOrderTime());
        orderDTO.setOrderType(orderEntity.getOrderType());
        orderDTO.setOrderAddress(orderEntity.getOrderAddress());
        orderDTO.setOrderTomaster(orderEntity.getOrderTomaster());
        orderDTO.setOrderTorider(orderEntity.getOrderTorider());
        orderDTO.setStoreFilename(orderEntity.getStoreEntity().getStoreFilename());
        if (!orderEntity.getOrderMenuEntityList().isEmpty()) {
            orderDTO.setMenuName(orderEntity.getOrderMenuEntityList().get(0).getMenuEntity().getMenuName());
        }

        return orderDTO;
    }
}
