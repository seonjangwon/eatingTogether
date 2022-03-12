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
import java.sql.Timestamp;
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
    @Autowired
    private RiderRepository rr;
    @Autowired
    private BasketRepository br;


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
        storeEntity.setStoreNumber(5L);
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
    @DisplayName("회원가입만")
    public void joinTest() {
        //회원
        IntStream.rangeClosed(1, 5).forEach(i -> {
            cs.save(new CustomerSaveDTO("이메일" + i, "비밀번호" + i, "닉네임" + i, "주소" + i, "핸드폰" + i, "동이름" + i));
        });
        System.out.println("회원가입 테스트 실행됨");
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
    }


    @Test
    @DisplayName("주문Test")
    //@Transactional
    //@Rollback
    public void orderTest() {
        //메뉴, 고객, 업체
        //회원
        CustomerEntity customerEntity = cr.findById(1l).get();
        System.out.println("회원 테스트 실행");
        System.out.println("회원정보: "+customerEntity);

        //업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(3l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreName("testStore");
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        System.out.println("bigCategoryEntity:"+bigCategoryEntity);
        System.out.println("업체가입 테스트 실행됨");

        //메뉴
        MenuEntity menuEntity = new MenuEntity();
        StoreCategoryEntity storeCategoryEntity = scr.findById(1l).get();
        menuEntity.setMenuName("우울한울면");
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setMenuPrice(12000);
        menuEntity.setMenuExplain("힝입니다");
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        Long menuNumber = mnr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity:"+menuEntity);
        System.out.println("메뉴저장 테스트 실행됨");



        //주문
        OrderNowEntity orderNowEntity = new OrderNowEntity();
        /*orderNowEntity = onr.findById(1l).get();*/
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStoreEntity(storeEntity);
        orderEntity.setCustomerEntity(customerEntity);
        orderEntity.setOrderMenuEntityList(orderEntity.getOrderMenuEntityList());
        orderEntity.setOrderPrice(menuEntity.getMenuPrice());
        orderEntity.setOrderAddress(customerEntity.getCustomerAddress());
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderTomaster("우울우울");
        orderEntity.setOrderTorider("ㅠㅠㅠ");
        orderEntity.setOrderType("주문");
        orderEntity.setOrderNowEntity(orderNowEntity);

        Long orderNumber = or.save(orderEntity).getOrderNumber();
        System.out.println("주문저장 테스트 실행됨");
        System.out.println("orderEntity:"+orderEntity);

        //주문상황관리
        orderNowEntity.setOrderEntity(orderEntity);
        orderNowEntity.setOrderNowStatus("요리");
        orderNowEntity.setOrderNowTime(orderEntity.getOrderTime().plusMinutes(10));
        Long orderNowNumber = onr.save(orderNowEntity).getOrderNowNumber();
        System.out.println("주문상황 테스트 실행됨");

        //이걸 했어야했나?
        menuEntity = mnr.findById(1l).get();
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrderEntity(orderEntity);
        orderMenuEntity.setOrderMenuCount(1);
        orderMenuEntity.setMenuEntity(menuEntity);
        Long orderMenuNumber = omr.save(orderMenuEntity).getOrderMenuNumber();
        System.out.println("오더메뉴저장 테스트 실행됨");

    }

    @Test
    //@Transactional
    //@Rollback
    public void 배달원등록테스트() {
        RiderEntity riderEntity = new RiderEntity();
        riderEntity.setRiderName("김밥짱");
        riderEntity.setRiderState("학익동");
        Long riderNumber = rr.save(riderEntity).getRiderNumber();
        riderEntity = new RiderEntity();
        riderEntity.setRiderName("이잇몸");
        riderEntity.setRiderState("학익1동");
        riderNumber = rr.save(riderEntity).getRiderNumber();
        riderEntity = new RiderEntity();
        riderEntity.setRiderName("박박사");
        riderEntity.setRiderState("문학동");
        riderNumber = rr.save(riderEntity).getRiderNumber();
        riderEntity = new RiderEntity();
        riderEntity.setRiderName("최최고");
        riderEntity.setRiderState("학익동");
        riderNumber = rr.save(riderEntity).getRiderNumber();
    }

    @Test
    @DisplayName("주문Test")
    //@Transactional
    //@Rollback
    public void 짱구분식주문테스트() {
        //메뉴, 고객, 업체
        //회원
        CustomerEntity customerEntity = cr.findById(7l).get();
        System.out.println("회원 테스트 실행");
        System.out.println("회원정보: "+customerEntity);

        //업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(3l).get();
        StoreEntity storeEntity = sr.findById(3l).get(); //짱구분식
        System.out.println("업체가입 테스트 실행됨");

        //메뉴
        StoreCategoryEntity storeCategoryEntity = scr.findById(1l).get();
        MenuEntity menuEntity = mnr.findById(16l).get();
        /*MenuEntity menuEntity2 = mnr.findById(22l).get();*/
        System.out.println("menuEntity:"+menuEntity);
        System.out.println("메뉴 불러왔음");



        //주문
        OrderNowEntity orderNowEntity = new OrderNowEntity();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStoreEntity(storeEntity);
        orderEntity.setCustomerEntity(customerEntity);
        orderEntity.setOrderMenuEntityList(orderEntity.getOrderMenuEntityList());
        orderEntity.setOrderPrice(menuEntity.getMenuPrice());
        orderEntity.setOrderAddress(customerEntity.getCustomerAddress());
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderTomaster("짱구분식 맛있죠");
        orderEntity.setOrderTorider("벨튀하세요");
        orderEntity.setOrderType("주문요청");
        orderEntity.setOrderNowEntity(orderNowEntity);

        Long orderNumber = or.save(orderEntity).getOrderNumber();
        System.out.println("주문저장 테스트 실행됨");
        System.out.println("orderEntity:"+orderEntity);

        //주문상황관리
        orderNowEntity.setOrderEntity(orderEntity);
        orderNowEntity.setOrderNowStatus("배달접수");
        orderNowEntity.setOrderNowTime(orderEntity.getOrderTime().plusMinutes(90));
        Long orderNowNumber = onr.save(orderNowEntity).getOrderNowNumber();
        System.out.println("주문상황 테스트 실행됨");

        //이걸 했어야했나?
        menuEntity = mnr.findById(16l).get();
        /*menuEntity2 = mnr.findById(22l).get();*/
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrderEntity(orderEntity);
        orderMenuEntity.setOrderMenuCount(1);
        orderMenuEntity.setMenuEntity(menuEntity);
        orderMenuEntity.setOrderMenuCount(1);
        /*orderMenuEntity.setMenuEntity(menuEntity2);*/
        Long orderMenuNumber = omr.save(orderMenuEntity).getOrderMenuNumber();
        System.out.println("오더메뉴저장 테스트 실행됨");

        //배달원?
        RiderEntity riderEntity = new RiderEntity();
        riderEntity.setRiderName("김홍석");
        riderEntity.setRiderState("문학동");
        Long riderNumber = rr.save(riderEntity).getRiderNumber();
        System.out.println("배달원저장 테스트");
    }

    @Test
    @DisplayName("주문Test")
    //@Transactional
    //@Rollback
    public void orderTest2() {
        //메뉴, 고객, 업체
        //회원
        CustomerEntity customerEntity = cr.findById(9l).get();
        System.out.println("회원정보: "+customerEntity);

        //업체
        /*BigCategoryEntity bigCategoryEntity = bcr.findById(17l).get();*/
        StoreEntity storeEntity = sr.findById(17L).get();
        /*System.out.println("bigCategoryEntity:"+bigCategoryEntity);*/
        System.out.println("업체가입 테스트 실행됨");

        //메뉴
        MenuEntity menuEntity = mnr.findById(28l).get();
        StoreCategoryEntity storeCategoryEntity = scr.findById(1l).get();
        System.out.println("메뉴저장 테스트 실행됨");

        //주문
        OrderNowEntity orderNowEntity = new OrderNowEntity();
        /*orderNowEntity = onr.findById(1l).get();*/
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStoreEntity(storeEntity);
        orderEntity.setCustomerEntity(customerEntity);
        orderEntity.setOrderMenuEntityList(orderEntity.getOrderMenuEntityList());
        orderEntity.setOrderPrice(menuEntity.getMenuPrice());
        orderEntity.setOrderAddress(customerEntity.getCustomerAddress());
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setOrderTomaster("우울우울");
        orderEntity.setOrderTorider("ㅠㅠㅠ");
        orderEntity.setOrderType("주문");
        orderEntity.setOrderNowEntity(orderNowEntity);

        Long orderNumber = or.save(orderEntity).getOrderNumber();
        System.out.println("주문저장 테스트 실행됨");
        System.out.println("orderEntity:"+orderEntity);

        //주문상황관리
        orderNowEntity.setOrderEntity(orderEntity);
        orderNowEntity.setOrderNowStatus("요리");
        orderNowEntity.setOrderNowTime(orderEntity.getOrderTime().plusMinutes(10));
        Long orderNowNumber = onr.save(orderNowEntity).getOrderNowNumber();
        System.out.println("주문상황 테스트 실행됨");

        //이걸 했어야했나?
        menuEntity = mnr.findById(1l).get();
        OrderMenuEntity orderMenuEntity = new OrderMenuEntity();
        orderMenuEntity.setOrderEntity(orderEntity);
        orderMenuEntity.setOrderMenuCount(1);
        orderMenuEntity.setMenuEntity(menuEntity);
        Long orderMenuNumber = omr.save(orderMenuEntity).getOrderMenuNumber();
        System.out.println("오더메뉴저장 테스트 실행됨");

        //장바구니?
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setBasketMenuCount(1);
        basketEntity.setMenuEntity(menuEntity);
        basketEntity.setCustomerEntity(customerEntity);
        basketEntity.setStoreEntity(storeEntity);
        Long basketNumber = br.save(basketEntity).getBasketNumber();
        System.out.println("장바구니 테스트 실행");
    }


}

