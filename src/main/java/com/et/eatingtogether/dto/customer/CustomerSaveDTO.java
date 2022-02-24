package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveDTO {
    private String customerEmail;
    private String customerPassword;
    private String customerNickname;
    private String customerAddress;
    private String customerPhone;
    private String customerDname;
    private Role role;



}
