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



    public CustomerSaveDTO(String s, String s1, String s2, String s3, String s4, String s5) {
    }
}
