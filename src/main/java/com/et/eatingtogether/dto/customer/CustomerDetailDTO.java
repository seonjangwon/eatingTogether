package com.et.eatingtogether.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailDTO {
    private Long customerNumber;

    private String customerEmail;
    private String customerPassword;
    private String customerNickname;
    private String customerAddress;
    private String customerPhone;
    private int customerGrade;
    private int customerPoint;
    private String customerDname;
}
