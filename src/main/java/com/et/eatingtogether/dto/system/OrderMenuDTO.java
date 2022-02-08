package com.et.eatingtogether.dto.system;

import com.et.eatingtogether.entity.OrderMenuEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenuDTO {
    private Long orderMenuNumber;
    private Long orderNumber;
    private Long menuNumber;
    private String menuName;
    private int orderMenuCount;
    private int orderMenuPrice;

    public static OrderMenuDTO toEntity(OrderMenuEntity orderMenuEntity){
        OrderMenuDTO orderMenuDTO = new OrderMenuDTO();
        orderMenuDTO.setOrderMenuNumber(orderMenuEntity.getOrderMenuNumber());
        orderMenuDTO.setOrderNumber(orderMenuEntity.getOrderMenuNumber());
        orderMenuDTO.setMenuNumber(orderMenuEntity.getOrderMenuNumber());
        orderMenuDTO.setMenuName(orderMenuEntity.getMenuEntity().getMenuName());
        orderMenuDTO.setOrderMenuCount(orderMenuEntity.getOrderMenuCount());
        orderMenuDTO.setOrderMenuPrice(orderMenuEntity.getMenuEntity().getMenuPrice());
        return orderMenuDTO;
    }
}
