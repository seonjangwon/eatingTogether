package com.et.eatingtogether;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.repository.CustomerRepository;
import com.et.eatingtogether.repository.StoreRepository;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class sunyoungTest {
    @Autowired
    private CustomerService cs;

    @Autowired
    private AdminService as;

    @Autowired
    private CustomerRepository ctr; // 회원

    @Autowired
    private StoreRepository sr; // 업체

    @Test
    @DisplayName("회원가입")
    public void saveCustomer(){
        IntStream.rangeClosed(1,10).forEach(i->{
            cs.save(new CustomerSaveDTO("이메일"+i,"비번"+i,"닉네임"+i,"주소"+i,"핸드폰"+i,"동이름"+i));
        });
//        CustomerSaveDTO customerSaveDTO = new CustomerSaveDTO();
//        customerSaveDTO.setCustomerEmail("gg");
//        customerSaveDTO.setCustomerPassword("비번");
//        customerSaveDTO.setCustomerAddress("주소");
//        customerSaveDTO.setCustomerDname("동");
//        customerSaveDTO.setCustomerNickname("g");
//
//        cs.save(customerSaveDTO);

    }

//    @Test
//    @DisplayName("쿠폰등록 테스트")
//    public void couponSave(){
//        IntStream.range(1, 3).forEach(i->{
//        as.save(new CouponDTO("쿠폰이름"+i, 1+i, 1+i));
//
//        });
//    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원목록 조회 테스트")
    public void customerListTest() {
        // 1. 회원정보를 저장한다
        // 2. 저장된 정보를 리스트로 불러와서 확인

        // 회원저장
        IntStream.rangeClosed(1, 5).forEach(i -> {
            cs.save(new CustomerSaveDTO("이메일" + i, "비밀번호" + i, "닉네임" + i, "주소" + i, "핸드폰" + i, "동이름" + i));
        });


        // 회원목록 출력
        List<CustomerDetailDTO> customerList = as.CustomerFindAll();

        assertThat(customerList.size()).isEqualTo(15);
    }


    @Test
    @Transactional
    @Rollback
    @DisplayName("회원삭제")
    public void deleteTest(){
        // 1. 회원등록 2. 회원 삭제
        CustomerSaveDTO customerSaveDTO = new CustomerSaveDTO();
        customerSaveDTO.setCustomerEmail("테스트이메일1");
        customerSaveDTO.setCustomerNickname("테스트닉네임1");

        Long customerNum = cs.save(customerSaveDTO);
        System.out.println("customerNum = " + customerNum);
        System.out.println("sunyoungTest.deleteTest" + as.findById(customerNum));

        // 2. 회원삭제
        ctr.deleteById(customerNum);

        // 테스트
        assertThrows(NoSuchElementException.class, () ->{
            assertThat(ctr.findById(customerNum).get()).isNull();
        });


    }


    @Test
    @DisplayName("업체등록 테스트")
    public void storeSaveTest(){
        IntStream.rangeClosed(1,10).forEach(i->{
            //as.storeSave(new StoreSaveDTO("이름"+i));
        });
    }






    @Test
    @Transactional
    @Rollback
    @DisplayName("업체삭제 테스트")
    public void storeDeleteTest(){
        // 1. given : 업체 생성 & 저장처리
        StoreSaveDTO storeSaveDTO = new StoreSaveDTO();
        storeSaveDTO.setStoreName("테스트업체명");
        Long storeNum = as.storeSave(storeSaveDTO);

        // 2. when : 테스트(삭제테스트)
        sr.deleteById(storeNum);

        // 3. then : 결과 검증
        assertThrows(NoSuchElementException.class, () -> {
            assertThat(sr.findById(storeNum).get()).isNull();
        });

    }

    



}
