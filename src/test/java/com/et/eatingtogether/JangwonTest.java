package com.et.eatingtogether;

import com.et.eatingtogether.dto.customer.BasketDTO;
import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;
import com.et.eatingtogether.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JangwonTest {
    @Autowired
    private CustomerRepository cr;
    @Autowired
    private CustomerService cs;
    @Autowired
    private BigCategoryRepository bcr;
    @Autowired
    private StoreRepository sr;
    @Autowired
    private DeliveryRepository dr;
    @Autowired
    private StoreCategoryRepository scr;
    @Autowired
    private MenuRepository mr;
    @Autowired
    private BasketRepository br;
    @Autowired
    private CouponRepository cpr;
    @Autowired
    private MyCouponRepository mcr;
    @Autowired
    private PointRepository pr;
    @Autowired
    private RiderRepository rr;
    @Autowired
    private DailySaleRepository dsr;

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

    @Test
    @DisplayName("bigCategorySave")
    public void bigCategorySave(){
        BigCategoryEntity bigCategoryEntity1 = new BigCategoryEntity();
        bigCategoryEntity1.setBigCategoryName("한식");
        bcr.save(bigCategoryEntity1);
        BigCategoryEntity bigCategoryEntity2 = new BigCategoryEntity();
        bigCategoryEntity2.setBigCategoryName("중식");
        bcr.save(bigCategoryEntity2);
        BigCategoryEntity bigCategoryEntity3 = new BigCategoryEntity();
        bigCategoryEntity3.setBigCategoryName("일식");
        bcr.save(bigCategoryEntity3);
        BigCategoryEntity bigCategoryEntity4 = new BigCategoryEntity();
        bigCategoryEntity4.setBigCategoryName("양식");
        bcr.save(bigCategoryEntity4);
        BigCategoryEntity bigCategoryEntity5 = new BigCategoryEntity();
        bigCategoryEntity5.setBigCategoryName("패스트푸드");
        bcr.save(bigCategoryEntity5);
        List<BigCategoryEntity> bigCategoryEntityList = bcr.findAll();
        System.out.println("bigCategoryEntityList = " + bigCategoryEntityList);
    }

    @Test
    //@Transactional
    //@Rollback
    @DisplayName("basketTest")
    public void basketTest(){
        // given
        // 회원
        CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
        customerDetailDTO.setCustomerEmail("123");
        customerDetailDTO.setCustomerPassword("123");
        Long customerNumber = cr.save(CustomerEntity.loginTest(customerDetailDTO)).getCustomerNumber();
        CustomerEntity customerEntity = cr.findById(customerNumber).get();
        System.out.println("customerEntity = " + customerEntity);
        // 업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreName("basketTestStore");
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        // 배달지
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setStoreEntity(storeEntity);
        deliveryEntity.setDeliveryDname("basketTestDname");
        deliveryEntity.setDeliveryPrice(1000);
        deliveryEntity.setDeliveryTime(30);
        int deliveryPrice = dr.save(deliveryEntity).getDeliveryPrice();
        // 스토어카테고리
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity);
        storeCategoryEntity.setStoreCategoryName("basketTestCategory");
        Long storeCategoryNumber = scr.save(storeCategoryEntity).getStoreCategoryNumber();
        // 메뉴
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity.setMenuName("basketTestMenuName");
        menuEntity.setMenuPrice(3000);
        Long menuNumber = mr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity = " + menuEntity);
        // 장바구니 추가
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setCustomerEntity(customerEntity);
        basketEntity.setStoreEntity(storeEntity);
        basketEntity.setMenuEntity(menuEntity);
        basketEntity.setBasketMenuCount(2);
        br.save(basketEntity).getBasketNumber();
        System.out.println("basketEntity = " + basketEntity);
        // when
//        CustomerEntity customerEntity1 = cr.findById(customerNumber).get();
//        List<BasketEntity> basketEntityList = customerEntity1.getBasketEntityList();
//        List<BasketDTO> basketDTOList = new ArrayList<>();
//        List<BasketDTO> basketDTOList1 = new ArrayList<>();
//        List<BasketDTO> basketDTOList2 = new ArrayList<>();
//        for (BasketEntity b : basketEntityList){
//            basketDTOList.add(BasketDTO.toEntity(b));
//        }
//        for (BasketEntity b : customerEntity1.getBasketEntityList()){
//            basketDTOList1.add(BasketDTO.toEntity(b));
//        }
//        for (BasketEntity b : customerEntity.getBasketEntityList()){
//            basketDTOList2.add(BasketDTO.toEntity(b));
//        }
//        for (BasketDTO b : basketDTOList){
//            System.out.println("b = " + b);
//        }
//        for (BasketDTO b : basketDTOList1){
//            System.out.println("b1 = " + b);
//        }
//        for (BasketDTO b : basketDTOList2){
//            System.out.println("b1 = " + b);
//        }

//
//        // then
//        assertThat(basketEntityList).isNullOrEmpty();
//        assertThat(basketDTOList.get(1).getMenuPrice()).isEqualTo(3000);
//        assertThat(basketDTOList.get(0).getMenuCount()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("basketTest2")
    public void basketTest2(){
        CustomerEntity customerEntity1 = cr.findById(17l).get();
        List<BasketEntity> basketEntityList = customerEntity1.getBasketEntityList();
        List<BasketDTO> basketDTOList = new ArrayList<>();
        List<BasketDTO> basketDTOList1 = new ArrayList<>();
        for (BasketEntity b : basketEntityList){
            basketDTOList.add(BasketDTO.toEntity(b));
        }
        for (BasketEntity b : customerEntity1.getBasketEntityList()){
            basketDTOList1.add(BasketDTO.toEntity(b));
        }
        for (BasketDTO b : basketDTOList){
            System.out.println("b = " + b);
        }
        for (BasketDTO b : basketDTOList1){
            System.out.println("b1 = " + b);
        }
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("deliveryPrice")
    public void deliveryPriceTest(){
        // 업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreName("basketTestStore");
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        // 배달지
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setStoreEntity(storeEntity);
        deliveryEntity.setDeliveryDname("basketTestDname");
        deliveryEntity.setDeliveryPrice(1000);
        int deliveryPrice = dr.save(deliveryEntity).getDeliveryPrice();
        DeliveryEntity deliveryEntity1 = dr.findByStoreEntityAndDeliveryDname(storeEntity, deliveryEntity.getDeliveryDname()).get();
        System.out.println("deliveryEntity1.getDeliveryDname() = " + deliveryEntity1.getDeliveryDname());
        System.out.println("deliveryEntity1.getDeliveryPrice() = " + deliveryEntity1.getDeliveryPrice());

    }

    @Test
    @DisplayName("17번 동이름 변경용")
    public void dnameUpdate(){
        //CustomerEntity customerEntity = cr.findById(17l).get(); 데탑용
        CustomerEntity customerEntity = cr.findById(17l).get(); // 노트북용
        customerEntity.setCustomerDname("basketTestDname");
        cr.save(customerEntity);
    }

    @Test
    @DisplayName("장바구니추가삭제용")
    public void basketadd(){
        CustomerEntity customerEntity = cr.findById(17l).get();
        StoreEntity storeEntity = sr.findById(10l).get();
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity);
        storeCategoryEntity.setStoreCategoryName("basketTestCategory2");
        Long storeCategoryNumber = scr.save(storeCategoryEntity).getStoreCategoryNumber();
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity.setMenuName("basketTestMenuName");
        menuEntity.setMenuPrice(3000);
        Long menuNumber = mr.save(menuEntity).getMenuNumber();
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setCustomerEntity(customerEntity);
        basketEntity.setStoreEntity(storeEntity);
        basketEntity.setMenuEntity(menuEntity);
        basketEntity.setBasketMenuCount(2);
        br.save(basketEntity).getBasketNumber();
    }

    @Test
    @DisplayName("쿠폰 생성용")
    public void couponAdd(){
        CustomerEntity customerEntity = cr.findById(17l).get();

//        CouponEntity couponEntity = new CouponEntity();
//        couponEntity.setCouponName("testCoupon1");
//        couponEntity.setCouponCondition(1000);
//        couponEntity.setCouponPrice(1000);
//        cpr.save(couponEntity);
        CouponEntity couponEntity2 = new CouponEntity();
        couponEntity2.setCouponName("testCoupon123");
        couponEntity2.setCouponCondition(1000000);
        couponEntity2.setCouponPrice(1000);
        cpr.save(couponEntity2);

//        MyCouponEntity myCouponEntity = new MyCouponEntity();
//        myCouponEntity.setCustomerEntity(customerEntity);
//        myCouponEntity.setCouponEntity(couponEntity);
//        mcr.save(myCouponEntity);
        MyCouponEntity myCouponEntity2 = new MyCouponEntity();
        myCouponEntity2.setCustomerEntity(customerEntity);
        myCouponEntity2.setCouponEntity(couponEntity2);
        mcr.save(myCouponEntity2);
    }
    @Test
    @DisplayName("쿠폰 생성용2")
    public void couponAdd2(){
        CustomerEntity customerEntity = cr.findById(17l).get();

        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("testCoupon1");
        couponEntity.setCouponCondition(1000);
        couponEntity.setCouponPrice(2000);
        cpr.save(couponEntity);

        MyCouponEntity myCouponEntity = new MyCouponEntity();
        myCouponEntity.setCustomerEntity(customerEntity);
        myCouponEntity.setCouponEntity(couponEntity);
        mcr.save(myCouponEntity);
    }
    @Test
    @DisplayName("포인트 생성용1")
    public void pointAdd(){
        CustomerEntity customerEntity = cr.findById(17l).get();
        customerEntity.setCustomerPoint(customerEntity.getCustomerPoint()+1000);
        cr.save(customerEntity);
    }

    @Test
    @DisplayName("업체 이메일 비번 생성용")
    public void storeEmailPasswordAdd(){
        StoreEntity storeEntity = sr.findById(1l).get();
        storeEntity.setStoreEmail("qwe");
        storeEntity.setStorePassword("qwe");
        sr.save(storeEntity);
    }

    @Test
    @DisplayName("업체 생성용")
    public void storeAdd(){
        // 업체
        BigCategoryEntity bigCategoryEntity = bcr.findById(1l).get();
        StoreEntity storeEntity = new StoreEntity();
        storeEntity.setBigCategoryEntity(bigCategoryEntity);
        storeEntity.setStoreName("basketTestStore22");
        storeEntity.setStoreEmail("a");
        storeEntity.setStorePassword("a");
        Long storeNumber = sr.save(storeEntity).getStoreNumber();
        // 배달지
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setStoreEntity(storeEntity);
        deliveryEntity.setDeliveryDname("basketTestDname");
        deliveryEntity.setDeliveryPrice(1000);
        deliveryEntity.setDeliveryTime(30);
        int deliveryPrice = dr.save(deliveryEntity).getDeliveryPrice();
        // 스토어카테고리
        StoreCategoryEntity storeCategoryEntity = new StoreCategoryEntity();
        storeCategoryEntity.setStoreEntity(storeEntity);
        storeCategoryEntity.setStoreCategoryName("basketTestCategory");
        Long storeCategoryNumber = scr.save(storeCategoryEntity).getStoreCategoryNumber();
        // 메뉴
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setStoreEntity(storeEntity);
        menuEntity.setStoreCategoryEntity(storeCategoryEntity);
        menuEntity.setMenuName("basketTestMenuName22");
        menuEntity.setMenuPrice(4000);
        Long menuNumber = mr.save(menuEntity).getMenuNumber();
        System.out.println("menuEntity = " + menuEntity);
    }

    @Test
    @DisplayName("빅카테고리 생성용")
    public void BCAdd (){
        Optional<StoreEntity> storeEntity = sr.findById(18l);
        Optional<BigCategoryEntity> bigCategoryEntity = bcr.findById(1l);
        storeEntity.get().setBigCategoryEntity(bigCategoryEntity.get());
        sr.save(storeEntity.get());
    }

    @Test
    @DisplayName("라이더 생성용")
    public void riderAdd(){
        RiderEntity riderEntity = new RiderEntity();
        riderEntity.setRiderName("테스트라이더1");
        riderEntity.setRiderState("대기");
        rr.save(riderEntity);
        RiderEntity riderEntity1 = new RiderEntity();
        riderEntity1.setRiderName("테스트라이더2");
        riderEntity1.setRiderState("대기");
        rr.save(riderEntity1);
    }

    @Test
    @DisplayName("일매출생성용")
    public void dailySaleTest(){
        StoreEntity storeEntity = sr.findById(22l).get();
        DailySaleEntity dailySaleEntity = new DailySaleEntity();
        LocalDate date = LocalDate.now();
        dailySaleEntity.setDailySaleTime(date);
        dailySaleEntity.setDailySalePrice(1000);
        dailySaleEntity.setStoreEntity(storeEntity);
        dsr.save(dailySaleEntity);
        Optional<DailySaleEntity> dailySaleEntity1 = dsr.findByDailySaleTimeAndStoreEntity(LocalDate.now(),storeEntity);
        System.out.println("dailySaleEntity1 = " + dailySaleEntity1.get().getDailySaleTime());
        System.out.println("dailySaleEntity1 = " + dailySaleEntity1.get().getDailySalePrice());
    }

}
