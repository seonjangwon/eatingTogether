package com.et.eatingtogether;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.BigCategoryDTO;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import com.et.eatingtogether.service.StoreService;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JiwonTest {
    @Autowired
    private StoreRepository sr;
    @Autowired
    private BigCategoryRepository bcr;
    @Autowired
    private StoreCategoryRepository scr;
    @Autowired
    private MenuRepository mnr;
    @Autowired
    private CustomerService cs;
    @Autowired
    private CustomerRepository cr;
    @Autowired
    private AdminService as;
    @Autowired
    private DeliveryRepository dr;
    @Autowired
    private OrderNowRepository onr;
    @Autowired
    private OrderRepository or;
    @Autowired
    private OrderMenuRepository omr;


    //✔ 테스트에 앞서 의존성 주입을 진행


    @Test
    @DisplayName("회원데이터 생성")
    public void newMembers() {
    }

    @Test
    //@Transactional
    //@Rollback
    @DisplayName("메뉴추가 힘들어")
    public void SaveMenuTest()  {

        //업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreNumber(19L);
        Long storeNumber = sr.save(storeEntity).getStoreNumber();

        //스토어카테고리
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity);
        storeCategoryEntity.setStoreCategoryName("김밥류");
        Long storeCategoryNumber = scr.save(storeCategoryEntity).getStoreCategoryNumber();

        //메뉴
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity.setMenuName("SaveMenu");
        menuEntity.setMenuPrice(3000);
        Long menuNumber = mnr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity: "+menuEntity);
    }


    @Test
    @Transactional
    @Rollback
    @DisplayName("주문현황test 근데 주문이 안되는구만.")
    public void orderNowTest()  {
        //회원
        IntStream.rangeClosed(1, 5).forEach(i -> {
            cs.save(new CustomerSaveDTO("이메일" + i, "비밀번호" + i, "닉네임" + i, "주소" + i, "핸드폰" + i, "동이름" + i));
        });
        System.out.println("회원가입 테스트 실행됨");
        //업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreName("testStore");
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        System.out.println("bigCategoryEntity:"+bigCategoryEntity);
        System.out.println("업체가입 테스트 실행됨");
        //메뉴
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuName("testMenu");
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setMenuPrice(100);
        Long menuNumber = mnr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity:"+menuEntity);
        System.out.println("메뉴저장 테스트 실행됨");
        //배달지
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setDeliveryDname("testDong");
        deliveryEntity.setDeliveryPrice(100);
        deliveryEntity.setDeliveryTime(20);
        Long deliveryNumber = dr.save(deliveryEntity).getDeliveryNumber();
        System.out.println("배달지 테스트 실행됨");
        /*//주문
        OrderEntity orderEntity = new OrderEntity();
        CustomerEntity customerEntity1 = cr.findById(1l).get();
        StoreEntity storeEntity1 = sr.findById(1l).get();
        OrderNowEntity orderNowEntity = onr.findById(1l).get();
        orderEntity.setCustomerEntity(customerEntity1);
        orderEntity.setStoreEntity(storeEntity1);
        orderEntity.setOrderNowEntity(orderNowEntity);
        orderEntity.setOrderAddress("냠냠동");
        orderEntity.setOrderPrice(4000);
        Long orderNumber = or.save(orderEntity).getOrderNumber();
        System.out.println("주문 테스트 실행됨");*/
    }

    @Test
    @DisplayName("주문Test")
    //@Transactional
    //@Rollback
    public void orderTest() {
        //메뉴, 고객, 업체
        //회원
        CustomerEntity customerEntity = cr.findById(60l).get();
        System.out.println("회원가입 테스트 실행됨");
        System.out.println("회원정보: "+customerEntity);

        //업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = sr.findByStoreEmail("888");
        System.out.println("bigCategoryEntity:"+bigCategoryEntity);
        System.out.println("업체가입 테스트 실행됨");

        //메뉴
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenuName("testMenu");
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setMenuPrice(100);
        Long menuNumber = mnr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity:"+menuEntity);
        System.out.println("메뉴저장 테스트 실행됨");


        //주문
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStoreEntity(storeEntity);
        orderEntity.setCustomerEntity(customerEntity);
        orderEntity.setOrderMenuEntityList(orderEntity.getOrderMenuEntityList());
        orderEntity.setOrderPrice(15000);
        orderEntity.setOrderAddress("우걱우걱동");
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderTomaster("^^");
        orderEntity.setOrderTorider("빨리천천히");
        orderEntity.setOrderType("주문");

        Long orderNumber = or.save(orderEntity).getOrderNumber();
        System.out.println("주문저장 테스트 실행됨");
        System.out.println("orderEntity:"+orderEntity);

        //이걸 했어야했나?
        menuEntity = mnr.findById(4l).get();
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrderEntity(orderEntity);
        orderMenuEntity.setOrderMenuCount(2);
        orderMenuEntity.setMenuEntity(menuEntity);
        Long orderMenuNumber = omr.save(orderMenuEntity).getOrderMenuNumber();
        System.out.println("오더메뉴저장 테스트 실행됨");

    }

}

