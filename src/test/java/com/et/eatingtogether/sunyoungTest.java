package com.et.eatingtogether;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.review.ReviewSaveDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.store.StoreCategoryDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.Ordered;
import org.springframework.test.annotation.Rollback;

import javax.lang.model.SourceVersion;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private MenuRepository mr; // 메뉴

    @Autowired
    private ReviewRepository rr; //리뷰

    @Autowired
    private StoreCategoryRepository scr; // 스토어 카테고리


    @Autowired
    private OrderMenuRepository omr; // 내가 주문한거 저장


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


    @Test
//    @Transactional
//    @Rollback
    @DisplayName("리뷰테스트")

    public void reviewTest() throws IOException {
        // 리뷰정보에서 필요한 것 : 회원, 업체, 메뉴, 리뷰내용, 리뷰작성시간, 작성자(회원 또는 로그인이메일?), 파일, 별점
        //given
        //회원
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setCustomerEmail("테스트이메일!");
        customerDetailDTO.setCustomerPassword("1");
        Long customerNumber = ctr.save(CustomerEntity.loginTest(customerDetailDTO)).getCustomerNumber();

        CustomerEntity customerEntity = ctr.findById(customerNumber).get();


        // 업체
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreName("테스트가게1");
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        StoreEntity storeEntity1 = sr.findById(storeNumber).get();

        // 스토어 카테고리
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity1);
        storeCategoryEntity.setStoreCategoryName("한식");
        scr.save(storeCategoryEntity);


        // 업체에 메뉴 생성 - 업체번호 필요.
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStoreEntity(storeEntity1);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity.setMenuName("된장찌개");

        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.setStoreEntity(storeEntity1);
        menuEntity2.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity2.setMenuName("포케");

        mr.save(menuEntity);
        mr.save(menuEntity2);

        List<MenuEntity> byStoreEntity = mr.findByStoreEntity(storeEntity1); // storeEntity1의 메뉴리스트


        // 주문번호엔티티 => 회원이 주문한 스토어의 메뉴에서 메뉴번호가 필요함
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setMenuEntity(menuEntity);

        OrderMenuEntity orderMenuEntity2 = new OrderMenuEntity();
        orderMenuEntity.setMenuEntity(menuEntity2);

        OrderMenuEntity save = omr.save(orderMenuEntity);
        OrderMenuEntity save1 = omr.save(orderMenuEntity2);

        // 주문한 메뉴목록에 위에 생성한 메뉴엔티티 2개를 담음
        List<OrderMenuEntity> orderMenuEntityList = new ArrayList<>();
        orderMenuEntityList.add(save);
        orderMenuEntityList.add(save1);


//        // 메뉴를 주문해!
//        // 주문한다! 테스트가게1의 메뉴리스트에서 메뉴를 골라 주문한다!
//        // 회원번호, 가게번호, 주문하는 메뉴가 뭔지(orderMenuEntityList)


        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerEntity(customerEntity); //회원
        orderEntity.setStoreEntity(storeEntity); //업체정보
        orderEntity.setOrderPrice(3000); // 가격
        orderEntity.setOrderMenuEntityList(orderMenuEntityList);

        System.out.println("orderEntity.getOrderMenuEntityList() = " + orderEntity.getOrderMenuEntityList().get(0));

////
////
////        // menuEntity를 orderMenuEntity에 넣어!
//
//
//
//
//
//
//
//
//
//        System.out.println("orderEntity = " + orderEntity.getOrderMenuEntityList().get(0).getMenuEntity().getMenuName());
//
//
//
//        // 리뷰등록 테스트
//        ReviewEntity reviewEntity = new ReviewEntity();
//        reviewEntity.setReviewContents("재밌다");
//        reviewEntity.setMenuName(menuEntityList.get(0).getMenuName());
//        reviewEntity.setCustomerEntity(customerEntity);
//        reviewEntity.setStoreEntity(storeEntity);
//
//        // 파일 : list라 for문을 사용하나..?
//        ReviewFileEntity reviewFileEntity = new ReviewFileEntity();
//        reviewFileEntity.setReviewFilename("테스트 파일이름");
//        reviewFileEntity.setReviewEntity(reviewEntity);
//
//
//
//        System.out.println("reviewFileEntity.getFileName = " + reviewFileEntity.getReviewFilename());
//
//        System.out.println("reviewEntity = " + reviewEntity.getMenuName());
//        System.out.println("reviewEntity = " + reviewEntity.getCustomerEntity().getCustomerNumber());
//        System.out.println("reviewEntity = " + reviewEntity.getStoreEntity().getStoreName());
//
////        System.out.println("reviewEntity = " + reviewEntity.getReviewFileEntityList().get(0).getReviewFilename());
//


//        rr.save(reviewEntity);


    }




}
