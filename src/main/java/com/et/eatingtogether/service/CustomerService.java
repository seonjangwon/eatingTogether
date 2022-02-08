package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;

import java.util.List;

public interface CustomerService {
    void login(CustomerDetailDTO customerDetailDTO);

    CustomerDetailDTO findByEmail(String customerLoginEmail);

    String update(CustomerDetailDTO customerDetailDTO);

    List<OrderDTO> orderList();

    OrderDTO findOrder(Long orderNumber);

    List<OrderMenuDTO> orderMenu(Long orderNumber);

    CustomerDetailDTO findById(Long customerNumber);
}
