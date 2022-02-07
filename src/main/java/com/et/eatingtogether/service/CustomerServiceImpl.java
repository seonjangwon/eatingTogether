package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository cr;
    private final HttpSession session;

    @Override
    public void login(CustomerDetailDTO customerDetailDTO) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail(customerDetailDTO.getCustomerEmail());
        if(customerEntity.isPresent()){ // 있으면
            if(customerEntity.get().getCustomerPassword().equals(customerDetailDTO.getCustomerPassword())){ // 일치하면
                System.out.println("로그인");
                session.setAttribute("customerLoginEmail",customerEntity.get().getCustomerEmail());
            } else { // 틀리면
                System.out.println("비밀번호");
                throw new IllegalStateException("이메일 또는 비밀번호가 틀립니다");
            }
        } else { // 없으면
            System.out.println("이메일");
            throw new IllegalStateException("이메일 또는 비밀번호가 틀립니다");
        }

    }
}
