package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.review.ReplyDetailDTO;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.*;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.et.eatingtogether.dto.store.DailySaleDTO.toDailySaleDTO;
import static com.et.eatingtogether.dto.store.MenuDTO.toMenuDetailDTO;
import static com.et.eatingtogether.dto.store.StoreDetailDTO.toStoreDetailDTO;
import static com.et.eatingtogether.dto.system.BigCategoryDTO.toBCDetailDTO;
import static com.et.eatingtogether.dto.system.OrderDTO.toStoreOrderDetailDTO;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository sr;
    private final BigCategoryRepository bcr;
    private final StoreCategoryRepository scr;
    private final MenuRepository mnr;
    private final HttpSession session;
    private final DeliveryRepository dr;
    private final OrderNowRepository onr;
    private final OrderRepository or;
    private final CustomerRepository cr;
    private final RiderRepository rr;
    private final DailySaleRepository dsr;
    private final ReplyRepository rpr;

    @Override
    public boolean login(StoreLoginDTO storeLoginDTO) {
        System.out.println("StoreSerivceImpl.login");
        StoreEntity storeEntity = sr.findByStoreEmail(storeLoginDTO.getStoreEmail());
        if (storeEntity != null) {
            if (storeEntity.getStorePassword().equals(storeLoginDTO.getStorePassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Long save(StoreSaveDTO storeSaveDTO) throws IOException {
        System.out.println("StoreServiceImpl.storeSave");

        //가게사진 추가
        if (!storeSaveDTO.getStoreFile().isEmpty()) {
            MultipartFile storeFile = storeSaveDTO.getStoreFile();
            String storeFilename = storeFile.getOriginalFilename();
            storeFilename = System.currentTimeMillis() + "-" + storeFilename;
            System.out.println("파일이름: " + storeFilename);
            String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename; // 지원이꺼
/*            String savePath = "C:\\development_psy\\source\\springboot\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename; // 내꺼*/
            if (!storeFile.isEmpty()) {
                storeFile.transferTo(new File(savePath));
            }
            storeSaveDTO.setStoreFilename(storeFilename);
        }

        BigCategoryEntity bigCategoryEntity = bcr.findByBigCategoryNumber(storeSaveDTO.getBigCategoryNumber());
        StoreEntity storeEntity = StoreEntity.toSaveStore(storeSaveDTO, bigCategoryEntity);

        //0210 사용자가 입력한 이메일 중복체크
        StoreEntity emailCheckResult = sr.findByStoreEmail(storeSaveDTO.getStoreEmail());
        if (emailCheckResult != null) { //64. 입력한 email이 null이 아닐 경우 65.예외 발생
            throw new IllegalStateException("중복된 이메일입니다.");
            // 예외 종류: IllegalStateException, 예외 메세지: 중복된 이메일입니다.
        }

        return sr.save(storeEntity).getStoreNumber();
    }

    //0219
    @Override
    public String idDuplicate(String storeEmail) {
        StoreEntity emailCheckResult = sr.findByStoreEmail(storeEmail);
        /*StoreEntity emailCheckResult = sr.findByStoreEmail(storeSaveDTO.getStoreEmail());*/
        /*String result = sr.findByStoreEmail(storeEmail);*/
        if (emailCheckResult == null) {
            return "ok";
        } else {
            return "no";
        }
    }

    @Override
    public List<StoreDetailDTO> findAll() {
        List<StoreEntity> storeEntityList = sr.findAll();
        List<StoreDetailDTO> storeList = new ArrayList<>();
        for (StoreEntity se1 : storeEntityList) {
            storeList.add(toStoreDetailDTO(se1));
        }
        System.out.println("StoreServiceImpl.categoryFindAll");
        return storeList;
    }

    @Override
    public List<BigCategoryDTO> findAllBc() {
        List<BigCategoryEntity> bigCategoryEntityList = bcr.findAll();
        List<BigCategoryDTO> bcList = new ArrayList<>();
        for (BigCategoryEntity bce : bigCategoryEntityList) {
            bcList.add(toBCDetailDTO(bce));
        }
        System.out.println("ServiceImpl.findAllBc.해치웠나");
        return bcList;
    }

    // store/category/{bigCategoryNumber} 하기위해.


    //0214
    @Override
    public StoreDetailDTO findById(String storeName) {
        System.out.println("StoreServiceImpl.findByStore");
        return StoreDetailDTO.toStoreDetailDTO(sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail")));
    }

    @Override
    public void saveMenu(MenuDTO menuDTO, StoreCategoryEntity storeCategoryEntity) throws IOException {
        System.out.println("StoreServiceImpl.menuSave");

        MultipartFile menuFile = menuDTO.getMenuFile();
        String menuFilename = menuFile.getOriginalFilename();
        menuFilename = System.currentTimeMillis() + menuFilename;

        String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + menuFilename; // 지원이꺼 + menuFilename;
        /*String savePath = "C:\\Users\\wkddn\\Desktop\\wkddnjs\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + menuFilename;*/
        if (!menuFile.isEmpty()) {
            menuFile.transferTo(new File(savePath));
        }
        menuDTO.setMenuFilename(menuFilename);

        StoreEntity storeEntity = sr.findById(menuDTO.getStoreNumber()).get();
//        StoreCategoryEntity storeCategoryEntity = scr.findById(menuDTO.getStoreCategoryNumber()).get();
        MenuEntity menuEntity = MenuEntity.toSaveMenuEntity(menuDTO, storeEntity, storeCategoryEntity);

        mnr.save(menuEntity);
    }

    @Override
    public List<StoreCategoryDTO> categoryList() {
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail")); //session은 object class로 값을 가져오기때문에 String으로 형변환을 해준다.
        List<StoreCategoryDTO> storeCategoryDTOList = new ArrayList<>();    // List<스토어카테고리의 정보가 있는> storeCategoryDTOList를 새로이 List화 시킨다.
        if (!storeEntity.getStoreCategoryEntityList().isEmpty()) {          // if, storeEntity의 StoreCategoryEntityList가 Empty가 아닌 경우
            for (StoreCategoryEntity c : storeEntity.getStoreCategoryEntityList()) { // for문을 이용해 storeEntity의 StoreCategryEntityList를 c에 담는다.
                storeCategoryDTOList.add(StoreCategoryDTO.toEntity(c));     // StoreCategoryDTO.toEntity(c) 라는 값을. storeCategoryDTOList에 add-추가한다.
            }
        }
        return storeCategoryDTOList;
    }

    @Override
    public StoreCategoryEntity categorySave(Long storeNumber, String storeCategoryName) {
        StoreCategoryDTO storeCategoryDTO = new StoreCategoryDTO();
        storeCategoryDTO.setStoreCategoryName(storeCategoryName);
        StoreEntity storeEntity = sr.findById(storeNumber).get();
        Long storeCategoryNumber = scr.save(StoreCategoryEntity.saveStoreCategory(storeCategoryDTO, storeEntity)).getStoreCategoryNumber();
        StoreCategoryEntity storeCategoryEntity = scr.findById(storeCategoryNumber).get();
        return storeCategoryEntity;
    }

    @Override
    public StoreCategoryEntity findCategory(Long storeCategoryNumber) {
        return scr.findById(storeCategoryNumber).get();
    }

    @Override
    public StoreDetailDTO findByNumber(Long storeNumber) {
        return StoreDetailDTO.toStoreDetailDTO(sr.findById(storeNumber).get());
    }

    @Override
    public List<MenuDTO> menuFindAll(Long storeNumber) {
        StoreEntity storeEntity = sr.findById(storeNumber).get();
        List<MenuEntity> menuEntityList = mnr.findByStoreEntity(storeEntity);

        List<MenuDTO> menuList = new ArrayList<>();
        for (MenuEntity menu : menuEntityList) {
            menuList.add(toMenuDetailDTO(menu));
        }
        return menuList;
    }

    // 0218 bigCategoryNum 눌렀을 때 해당 bcNum 을 갖고있는 스토어만 띄우는 기능
    @Override
    public List<StoreDetailDTO> findByBcNumber(Long bigCategoryNumber) {
        BigCategoryEntity bigCategoryEntity = bcr.findById(bigCategoryNumber).get();
//        List<StoreEntity> storeEntityList = sr.findByBigCategoryEntity(bigCategoryEntity);
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<DeliveryEntity> deliveryEntities = dr.findByDeliveryDname(customerEntity.get().getCustomerDname());
        List<StoreEntity> storeEntityList = new ArrayList<>();
        for (DeliveryEntity d : deliveryEntities) {
            if (d.getStoreEntity().getBigCategoryEntity().equals(bigCategoryEntity)) {
                storeEntityList.add(d.getStoreEntity());
            }
        }

        /*BigCategoryEntity bigCategoryEntity = scr.findById(bigCategoryNumber).get();
        List<StoreEntity> storeEntityList = sr.findByStoreCategoryEntity(storeCategoryEntity);*/

        List<StoreDetailDTO> storeList = new ArrayList<>();

        for (StoreEntity se : storeEntityList) {
            storeList.add(toStoreDetailDTO(se));
        }
        return storeList;
    }


    //0217 메뉴수정을 위한.
    @Override
    public MenuDetailDTO findByMenu(Long menuNumber) {
        Optional<MenuEntity> optionalMenuEntity = mnr.findById(menuNumber);
        MenuDetailDTO menuDetailDTO = null;

        if (optionalMenuEntity.isPresent()) {
            MenuEntity menuEntity = optionalMenuEntity.get();
            menuDetailDTO = MenuDetailDTO.toDetailMenu(menuEntity);
        }
        return menuDetailDTO;
    }

    @Override
    public Long updateMenu(MenuDetailDTO menuDetailDTO) throws IOException {
        System.out.println("updateMenu: " + menuDetailDTO);
        StoreEntity storeEntity = null;
        StoreCategoryEntity storeCategoryEntity = null;

        MultipartFile menufile = menuDetailDTO.getMenuFile();
        String menuFilename = menufile.getOriginalFilename();
        menuFilename = System.currentTimeMillis() + "-" + menuFilename;
        //저장되는 곳
        String updatePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + menuFilename;

        if (!menuFilename.isEmpty()) {
            menufile.transferTo(new File(updatePath));
        }
        storeEntity = sr.findById(menuDetailDTO.getStoreNumber()).get();
        storeCategoryEntity = scr.findById(menuDetailDTO.getStoreCategoryNumber()).get();
        menuDetailDTO.setMenuFilename(menuFilename);

        System.out.println("StoreServiceImpl.updateMenu");
        return mnr.save(MenuEntity.toUpdateMenuEntity(menuDetailDTO, storeEntity, storeCategoryEntity)).getMenuNumber();
    }

    @Override
    public void deleteByMenu(Long menuNumber) {
        System.out.println("StoreServiceImpl.deleteByMenu");
        mnr.deleteById(menuNumber);
    }

    @Override
    public void deliverySave(DeliveryDTO deliveryDTO) {
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail"));
        DeliveryEntity deliveryEntity = DeliveryEntity.toSaveDeliveryEntity(deliveryDTO, storeEntity);
        System.out.println("오류가 안난다고...?");
        dr.save(deliveryEntity);
    }

    //0224. 주문상세보기
    @Override
    public OrderDTO findByOrder(Long orderNumber) {
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail"));
        List<OrderEntity> orderEntityList = storeEntity.getOrderEntityList();
        for (OrderEntity o : orderEntityList) {
            if (o.getOrderNumber().equals(orderNumber)) {
                return OrderDTO.toStoreOrderDetailDTO(o);
            }
        }
        return null;
    }

    @Override
    public List<OrderMenuDTO> orderMenu(Long orderNumber) {
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail"));
        List<OrderEntity> orderEntityList = storeEntity.getOrderEntityList();
        for (OrderEntity o : orderEntityList) {
            if (o.getOrderNumber().equals(orderNumber)) {
                List<OrderMenuEntity> orderMenuEntityList = o.getOrderMenuEntityList();
                List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
                for (OrderMenuEntity ome : orderMenuEntityList) {
                    orderMenuDTOList.add(OrderMenuDTO.toEntity(ome));
                }
                return orderMenuDTOList;
            }
        }
        return null;
    }

    @Override
    public List<OrderDTO> findOrderAll(String storeEmail) {
        //storeEmail에 해당하는 orderList를 가져옵니다

        StoreEntity storeEntity = sr.findByStoreEmail(storeEmail);
        List<OrderEntity> orderEntityList = or.findByStoreEntity(storeEntity);
        List<OrderDTO> orderAll = new ArrayList<>();
        for (OrderEntity oe : orderEntityList) {
            orderAll.add(toStoreOrderDetailDTO(oe));
        }
        return orderAll;
    }

    @Override
    public String updateStore(StoreDetailDTO storeDetailDTO) {
        BigCategoryEntity bigCategoryEntity = bcr.findByBigCategoryNumber(storeDetailDTO.getBigCategoryNumber());
        Optional<StoreEntity> storeEntity = sr.findById(storeDetailDTO.getStoreNumber());
        if (storeEntity.get().getStoreNumber().equals(storeDetailDTO.getStoreNumber())) {
            //number값이 일치한다면
            sr.save(StoreEntity.toUpdate(storeDetailDTO, bigCategoryEntity));
            System.out.println("수정합니당");
            return "ok";
        } else {
            System.out.println("수정못해");
            //number값이 일치하지 않는다면
            return "no";
        }
    }

    //0304
    @Override
    public List<DailySaleDTO> findSaleAll(Long storeNumber) {
        Optional<StoreEntity> storeEntity = sr.findById(storeNumber);
        List<DailySaleEntity> dailySaleEntityList = dsr.findByStoreEntity(storeEntity.get());
        List<DailySaleDTO> dailySale = new ArrayList<>();
        for (DailySaleEntity ds : dailySaleEntityList) {
            dailySale.add(toDailySaleDTO(ds));
        }
        return dailySale;
    }

    @Override
    public List<StoreDetailDTO> search(String searchType, String keyword) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<StoreDetailDTO> storeDetailDTOList = new ArrayList<>();
        List<StoreEntity> storeEntityList = new CopyOnWriteArrayList<>();
        if (searchType.equals("menu")){
            // 메뉴 리스트 가져와서 스토어만 엔티티 리스트에 넣어서 디테일 디티오에 넣어서 사용하자
            List<MenuEntity> menuEntities = mnr.findByMenuNameContaining(keyword);
            for (MenuEntity m : menuEntities){
                for (DeliveryEntity d : m.getStoreEntity().getDeliveryEntityList()) {
                    if (customerEntity.get().getCustomerDname().equals(d.getDeliveryDname())) {
                        if (!storeEntityList.isEmpty()) {
                            for (StoreEntity s : storeEntityList) {
                                if (!s.getStoreEmail().equals(m.getStoreEntity().getStoreEmail())) {
                                    storeEntityList.add(m.getStoreEntity());
                                }
                            }
                        } else {
                            storeEntityList.add(m.getStoreEntity());
                        }
                    }
                }
            }
        } else if (searchType.equals("store")){
            List<StoreEntity> storeEntityList2 = sr.findByStoreNameContaining(keyword);
            for (StoreEntity s : storeEntityList2) {
                for (DeliveryEntity d : s.getDeliveryEntityList()){
                    if (customerEntity.get().getCustomerDname().equals(d.getDeliveryDname())){
                        storeEntityList.add(s);
                    }
                }
            }
        }
        for (StoreEntity s : storeEntityList){
            storeDetailDTOList.add(StoreDetailDTO.toStoreDetailDTO(s));
        }
        return storeDetailDTOList;
    }

    @Override
    public String riderStart(Long riderNumber, Long orderNumber) {
        // 라이더 상태 수정
        Optional<RiderEntity> riderEntity = rr.findById(riderNumber);
        riderEntity.get().setRiderState("배달중");
        rr.save(riderEntity.get());
        // 주문 내역 불러와서 주문 상태 수정
        OrderNowEntity orderNowEntity = or.findById(orderNumber).get().getOrderNowEntity();
        orderNowEntity.setOrderNowStatus("배달중");
        orderNowEntity.setRiderEntity(riderEntity.get());
        onr.save(orderNowEntity);
        return "ok";
    }

    @Override
    public String riderEnd(Long orderNumber) {
        OrderNowEntity orderNowEntity = or.findById(orderNumber).get().getOrderNowEntity();
        orderNowEntity.setOrderNowStatus("배달 완료");
        onr.save(orderNowEntity);
        RiderEntity riderEntity = orderNowEntity.getRiderEntity();
        riderEntity.setRiderState("대기");
        rr.save(riderEntity);
        return "ok";
    }

    @Override
    public void dailySale(Long orderNumber) {
        OrderEntity orderEntity = or.findById(orderNumber).get();
        StoreEntity storeEntity = sr.findByStoreEmail((String) session.getAttribute("storeLoginEmail"));

        Optional<DailySaleEntity> dailySaleEntity = dsr.findByDailySaleTimeAndStoreEntity(LocalDate.now(),storeEntity);
        if (!dailySaleEntity.isEmpty()){
            // 있다면
            dailySaleEntity.get().setDailySalePrice(dailySaleEntity.get().getDailySalePrice() + orderEntity.getOrderPrice());
            dsr.save(dailySaleEntity.get());
        } else {
            DailySaleEntity dailySaleEntity1 = new DailySaleEntity();
            dailySaleEntity1.setDailySalePrice(orderEntity.getOrderPrice());
            dailySaleEntity1.setDailySaleTime(LocalDate.now());
            dailySaleEntity1.setStoreEntity(storeEntity);
            dsr.save(dailySaleEntity1);
        }
    }
    @Override
    public List<ReviewDetailDTO> reviewStore(Long storeNumber) {
        Optional<StoreEntity> storeEntity = sr.findById(storeNumber);
        List<ReviewDetailDTO> reviewDetailDTOList = new ArrayList<>();
        for (ReviewEntity r : storeEntity.get().getReviewEntityList()) {
//            reviewDetailDTOList.add(ReviewDetailDTO.toEntity1(r));
            reviewDetailDTOList.add(ReviewDetailDTO.toEntity(r));
        }

        for (ReviewDetailDTO r : reviewDetailDTOList) {
            List<ReviewFileDTO> reviewFileDTOS = new ArrayList<>();

            Optional<ReplyEntity> replyEntity = null;
            if (r.getReplyNumber() != null) {
                replyEntity = rpr.findById(r.getReplyNumber());
                r.setReplyDetailDTO(ReplyDetailDTO.toEntity(replyEntity.get()));
            }


            for (ReviewFileEntity rf : r.getReviewFileEntityList()) {
                System.out.println("rf = " + rf.getReviewFilename());
                reviewFileDTOS.add(ReviewFileDTO.toEntity(rf));
            }

            r.setReviewFileDTOList(reviewFileDTOS);
        }
        System.out.println("서비스 임플에서 reviewDetailDTOList = " + reviewDetailDTOList);
        return reviewDetailDTOList;
    }
}
