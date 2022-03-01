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
import java.util.Optional;
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
    
    @Autowired
    private OrderRepository or; // 주문엔티티


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
    @Transactional
    @Rollback
    @DisplayName("리뷰테스트")

    public void reviewTest() throws IOException {
        // 리뷰정보에서 필요한 것 : 회원, 업체, 메뉴, 리뷰내용, 리뷰작성시간, 작성자(회원 또는 로그인이메일?), 파일, 별점
        //given
        //회원생성
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setCustomerEmail("테스트이메일!");
        customerDetailDTO.setCustomerPassword("1");
        
        // customerNumber , customerEntity 값 가져옴
        Long customerNumber = ctr.save(CustomerEntity.loginTest(customerDetailDTO)).getCustomerNumber();
        CustomerEntity customerEntity = ctr.findById(customerNumber).get();
        
        // 업체생성
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setStoreName("테스트가게1");
        
        // storeNumber, storeEntity 가져옴
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        StoreEntity storeSaveEntity = sr.findById(storeNumber).get();

        // 스토어 카테고리 생성
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeSaveEntity);
        storeCategoryEntity.setStoreCategoryName("한식");
        
        // 카테고리 db에 저장 / storeCategoryNumber, storeCategoryEntity 가져옴
        Long storeCategoryNumber = scr.save(storeCategoryEntity).getStoreCategoryNumber();
        StoreCategoryEntity storeCategorySaveEntity = scr.findById(storeCategoryNumber).get();

        // 스토어에 메뉴저장 : MenuEntity에 메뉴를 저장합시다
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuName("된장찌개");
        menuEntity.setStoreCategoryEntity(storeCategorySaveEntity);
        menuEntity.setStoreEntity(storeSaveEntity);
        menuEntity.setMenuPrice(5000);


        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.setMenuName("포케");
        menuEntity1.setStoreCategoryEntity(storeCategorySaveEntity);
        menuEntity1.setStoreEntity(storeSaveEntity);
        menuEntity1.setMenuPrice(10000);


        // mentEntity 인스턴스를 db에 저장! 저장한 menuEntity 객체를 사용해야 하니까 리턴값으로 가져오기
        MenuEntity menu = mr.save(menuEntity);
        MenuEntity menu1 = mr.save(menuEntity1);

        //위에서 save하고 불러온 MenuEntity를 list로 담기(특정 가게에 등록되어 있는 메뉴 리스트!)
        List<MenuEntity> menuEntityList = new ArrayList<>();
        menuEntityList.add(menu);
        menuEntityList.add(menu1);

        System.out.println("menuEntityList.get(0).getMenuName() = " + menuEntityList.get(0).getMenuName());
        System.out.println("menuEntityList.get(0).getMenuName() = " + menuEntityList.get(1).getMenuName());
        
        
        storeSaveEntity.setMenuEntityList(menuEntityList); // 가게db에 메뉴리스트 추가
        System.out.println("storeEntity1 = " + storeSaveEntity.getMenuEntityList().get(0).getMenuName()); // 0번인덱스 메뉴가 뭔지 확인
        
        List<MenuEntity> bystoreMenuList = mr.findByStoreEntity(storeSaveEntity); // storeEntity1 가게의 메뉴리스트
        System.out.println("bystoreMenuList = " + bystoreMenuList.get(0).getMenuName());
        System.out.println("bystoreMenuList = " + bystoreMenuList.get(1).getMenuName());

       // 주문엔티티 (회원이 주문할때)
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerEntity(customerEntity); // 회원번호
        orderEntity.setStoreEntity(storeSaveEntity); // 가게번호


        Long orderNumber = or.save(orderEntity).getOrderNumber(); // 주문번호
        OrderEntity orderEntity1 = or.findById(orderNumber).get(); // orderEntity


        // 회원이 주문한 메뉴 ( orderMenuEntity)
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();

        OrderMenuEntity orderMenuEntity1 = new OrderMenuEntity();
        orderMenuEntity1.setMenuEntity(storeSaveEntity.getMenuEntityList().get(1));

        OrderMenuEntity ordermenuSave = omr.save(orderMenuEntity);
        OrderMenuEntity ordermenuSave1 = omr.save(orderMenuEntity1);


        // 주문한 메뉴를 회원의 주문ENTITY에 리스트로 넣어준다

        List<OrderMenuEntity> orderMenuEntityList = or.findById(orderNumber).get().getOrderMenuEntityList();
        orderMenuEntityList.add(ordermenuSave);
        orderMenuEntityList.add(ordermenuSave1);

        // 회원 orderEntity에 리스트 추가
        orderEntity1.setOrderMenuEntityList(orderMenuEntityList);

        System.out.println("orderEntity1 = " + orderEntity1.getOrderMenuEntityList().get(0).getMenuEntity().getMenuName());
        System.out.println("orderEntity1 = " + orderEntity1.getOrderMenuEntityList().get(1).getMenuEntity().getMenuName());


        
//        rr.save(reviewEntity);


    }




}
