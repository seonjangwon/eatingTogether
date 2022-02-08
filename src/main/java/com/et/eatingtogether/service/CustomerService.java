package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;

public interface CustomerService {
    void login(CustomerDetailDTO customerDetailDTO);

    CustomerDetailDTO findByEmail(String customerLoginEmail);

    String update(CustomerDetailDTO customerDetailDTO);
}
