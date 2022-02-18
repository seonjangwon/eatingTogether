package com.et.eatingtogether.dto.system;

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
}
