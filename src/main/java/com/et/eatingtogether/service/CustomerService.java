package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;

public interface CustomerService {
    void login(CustomerDetailDTO customerDetailDTO);

    CustomerDetailDTO findByEmail(String customerLoginEmail);

    String update(CustomerDetailDTO customerDetailDTO);

    Long save(CustomerSaveDTO customerSaveDTO);

    String findByCustomerEmail(String customerEmail);
}
