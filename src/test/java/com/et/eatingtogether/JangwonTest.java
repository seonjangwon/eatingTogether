package com.et.eatingtogether;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.repository.CustomerRepository;
import com.et.eatingtogether.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class JangwonTest {
    @Autowired
    private CustomerRepository cr;
    @Autowired
    private CustomerService cs;

    @Test
    @Transactional
    @Rollback
    public void loginTest(){
        //given
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setCustomerEmail("123");
        customerDetailDTO.setCustomerPassword("123");
        cr.save(CustomerEntity.loginTest(customerDetailDTO));
        //when
        // 로그인
        CustomerDetailDTO customerDetailDTO1 = new CustomerDetailDTO();
        customerDetailDTO1.setCustomerEmail("123");
        customerDetailDTO1.setCustomerPassword("123");
        // 이메일 틀림
        CustomerDetailDTO customerDetailDTO2 = new CustomerDetailDTO();
        customerDetailDTO2.setCustomerEmail("1234");
        customerDetailDTO2.setCustomerPassword("123");
        // 비밀번호 틀림
        CustomerDetailDTO customerDetailDTO3 = new CustomerDetailDTO();
        customerDetailDTO3.setCustomerEmail("123");
        customerDetailDTO3.setCustomerPassword("1234");
        //then
        try {
            cs.login(customerDetailDTO);
        } catch (IllegalStateException e) {
            System.out.println("e1 = " + e);
        }
        try {
            cs.login(customerDetailDTO2);
        } catch (IllegalStateException e) {
            System.out.println("e2 = " + e);
        }
        try {
            cs.login(customerDetailDTO3);
        } catch (IllegalStateException e) {
            System.out.println("e3 = " + e);
        }
    }

}
