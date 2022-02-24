package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.security.Role;
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
    private Role role;

    public static CustomerDetailDTO toEntity(CustomerEntity customerEntity){
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setCustomerNumber(customerEntity.getCustomerNumber());
        customerDetailDTO.setCustomerEmail(customerEntity.getCustomerEmail());
        customerDetailDTO.setCustomerPassword(customerEntity.getCustomerPassword());
        customerDetailDTO.setCustomerNickname(customerEntity.getCustomerNickname());
        customerDetailDTO.setCustomerAddress(customerEntity.getCustomerAddress());
        customerDetailDTO.setCustomerPhone(customerEntity.getCustomerPhone());
        customerDetailDTO.setCustomerGrade(customerEntity.getCustomerGrade());
        customerDetailDTO.setCustomerPoint(customerEntity.getCustomerPoint());
        // 배달 가능 지역은 보여줄 필요 없겠지
        return customerDetailDTO;
    }
}
