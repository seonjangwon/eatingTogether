package com.et.eatingtogether.dto.system;

import com.et.eatingtogether.entity.OrderNowEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNowDTO {
    private Long orderNowNumber;
    private String orderNowStatus;
    private LocalDateTime orderNowTime;

    public static OrderNowDTO toOrderDetailDTO (OrderNowEntity orderNowEntity) {
        OrderNowDTO orderNowDTO = new OrderNowDTO();
        orderNowDTO.setOrderNowNumber(orderNowEntity.getOrderNowNumber());
        orderNowDTO.setOrderNowStatus(orderNowEntity.getOrderNowStatus());
        orderNowDTO.setOrderNowTime(orderNowEntity.getOrderNowTime());
        return orderNowDTO;
    }
}
