package com.et.eatingtogether.controller;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.store.*;
import com.et.eatingtogether.dto.system.*;
import com.et.eatingtogether.entity.StoreCategoryEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.service.AdminService;
import com.et.eatingtogether.service.CustomerService;
import com.et.eatingtogether.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes("storeLoginEmail")
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService ss;
    private final CustomerService cs;
    private final AdminService as;
    private final HttpSession session;


    @GetMapping("/category")
    public String bigCategoryMain(Model model) {
        System.out.println("StoreController.bigCategoryAll");

        List<BigCategoryDTO> bcList = ss.findAllBc();
        model.addAttribute("bcList", bcList);

//        List<StoreDetailDTO> storeList = ss.findAll();
//        model.addAttribute("storeList", storeList);
        //얘를 데려와야 정보를 가져오지않남? 단순 창띄우기니까 필요가 없을지도!
        return "store/categoryMain";
    }

    @GetMapping("/category/{bigCategoryNumber}")
    public String bigCategoryPage(@PathVariable Long bigCategoryNumber, Model model) {

        // 0218
        List<StoreDetailDTO> storeList = ss.findByBcNumber(bigCategoryNumber);

        if (storeList != null) {
            model.addAttribute("storeList", storeList);
            System.out.println("category/{bigCategoryNumber}");
        }
        // return "store/category/" + bigCategoryNumber; 경로상의 문제일 수도 있다고해서.
        return "store/category";
    }

    // 0214 지원 정리를 좀 해보았음... findById
    @GetMapping("/{storeNumber}")
    public String storeDetail(@PathVariable Long storeNumber, Model model) {
        System.out.println("매장상세내용 띄우기");
        StoreDetailDTO storeDetailDTO = ss.findByNumber(storeNumber);
        model.addAttribute("storeList", storeDetailDTO);

        // 0216 메뉴띄우기
        List<MenuDTO> menuList = ss.menuFindAll(storeNumber);
        model.addAttribute("menuList", menuList);
        System.out.println(menuList);

        List<ReviewDetailDTO> reviewDetailDTOList = ss.reviewStore(storeNumber);
        model.addAttribute("reviewList",reviewDetailDTOList);

        System.out.println(storeDetailDTO.getStoreName());
        System.out.println(storeDetailDTO);
        return "store/store";
    }


    // 0213 업체 로그인 후 관리페이지로 이동
    @GetMapping("/menu")
    public String menuForm(Model model) {
        System.out.println("addMenuForm");
        model.addAttribute("menuSave", new MenuDTO()); // 필드 생성용
        // 스토어카테고리
        List<StoreCategoryDTO> categoryDTOList = ss.categoryList();
        model.addAttribute("storeCategory", categoryDTOList);
        StoreDetailDTO storeList = ss.findById((String) session.getAttribute("storeLoginEmail"));
        model.addAttribute("storeNumber", storeList.getStoreNumber());
        return "store/menuSave";
    }
    // 모든것에 의미를 부여하지말자... 슬프다 똑똑해지고싶다 흑흑

    // 0214-15 지원 메뉴등록
    @PostMapping("/menu")
    public String menu(@Validated @ModelAttribute("menuSave") MenuDTO menuDTO) throws IOException {
        System.out.println("StoreController.addMenu");
        StoreCategoryEntity storeCategoryEntity;
        if (menuDTO.getStoreCategoryNumber() == 0) {
            storeCategoryEntity = ss.categorySave(menuDTO.getStoreNumber(), menuDTO.getStoreCategoryName());
        } else {
            storeCategoryEntity = ss.findCategory(menuDTO.getStoreCategoryNumber());
        }
        ss.saveMenu(menuDTO, storeCategoryEntity);
        return "redirect:/store/menuControl/" + storeCategoryEntity.getStoreEntity().getStoreEmail();
        /*return "redirect:/store/" + storeCategoryEntity.getStoreEntity().getStoreNumber();*/
    }

    //0216
    @PostMapping("/menuList")
    public @ResponseBody
    List<MenuDTO> menuAjax(@PathVariable Long storeNumber) {
        List<MenuDTO> menuList = ss.menuFindAll(storeNumber);
        System.out.println("storeController.List<MenuDTO> menuAjax");
        return menuList;
    }

    //0217 헉 이거 아니다 아 아니 맞다
    @GetMapping("/update/{menuNumber}")
    public String menuUpdateForm(@PathVariable Long menuNumber, Model model) {
        MenuDetailDTO menuDetail = ss.findByMenu(menuNumber);
        model.addAttribute("menu", menuDetail);
        System.out.println("StoreController.menuUpdateForm 실행");
        return "store/menuUpdate";
    }

    //왜 ㅠ
    @PutMapping("/update")
    @ResponseBody
    public String menuUpdate(//@RequestBody MenuDetailDTO menuDetailDTO,
                             @RequestParam(value = "menuFile", required = false) MultipartFile menuFile,
                             MultipartHttpServletRequest request,
                             @RequestParam("menuNumber") Long menuNumber,
                             @RequestParam("storeNumber") Long storeNumber,
                             @RequestParam("storeCategoryNumber") Long storeCategoryNumber,
                             @RequestParam("menuName") String menuName,
                             @RequestParam("menuPrice") int menuPrice,
                             @RequestParam("menuExplain") String menuExplain
    ) throws IOException {
        System.out.println("StoreController.menuUpdate 처리");
        System.out.println("menuFile = " + menuFile);
        System.out.println("menuNumber = " + menuNumber);
        System.out.println("menuName = " + menuName);
        System.out.println("menuPrice = " + menuPrice);
        System.out.println("menuExplain = " + menuExplain);
//        System.out.println("menuDetailDTO = " + menuDetailDTO);
        MenuDetailDTO menuDetailDTO = new MenuDetailDTO();
        menuDetailDTO.setMenuNumber(menuNumber);
        menuDetailDTO.setMenuName(menuName);
        menuDetailDTO.setMenuPrice(menuPrice);
        menuDetailDTO.setMenuExplain(menuExplain);
        menuDetailDTO.setMenuFile(menuFile);
        menuDetailDTO.setStoreNumber(storeNumber);
        menuDetailDTO.setStoreCategoryNumber(storeCategoryNumber);
        ss.updateMenu(menuDetailDTO);
        return "ok"; // ok는 memberUpdate의 ajax success 의 result 값으로 적용된다.
    }

                   /* @DeleteMapping ("/delete/{menuNumber}")
                    public @ResponseBody String menuDelete (@PathVariable Long menuNumber)  {
                        System.out.println("StoreController.menuDelete");
                        ss.deleteByMenu(menuNumber);
                        return "redirect:/store/storeMain";
                    }*/

    @DeleteMapping("/delete/{menuNumber}")
    public ResponseEntity menuDelete(@PathVariable Long menuNumber) {
        System.out.println("StoreController.menuDelete");
        ss.deleteByMenu(menuNumber);
        return new ResponseEntity(HttpStatus.OK);
    }


    //지원 0218
    @GetMapping("/delivery")
    public String storeDeliveryForm(Model model) {
        // 필요한 것, storeNumber? storeEntity 의 storeNumber
        // delivery의 정보.
        System.out.println("StoreController.storeDeliveryForm");
        model.addAttribute("DeliverySave", new DeliveryDTO());
        StoreDetailDTO storeDetailDTO = ss.findById((String) session.getAttribute("StoreLoginEmail"));
        model.addAttribute("storeNumber", storeDetailDTO.getStoreNumber());
        return "store/storeDelivery";
    }

    //지원 0220~
    @PostMapping("/delivery")
    public String storeDelivery(@Validated @ModelAttribute DeliveryDTO deliveryDTO) {
        System.out.println("StoreController.storeDelivery");
        for (DeliveryDTO d : deliveryDTO.getDeliveryDTOList()){
            System.out.println("d = " + d);
            ss.deliverySave(d);
        }
//        ss.deliverySave(deliveryDTO);
        //등록만 하는거 맞잖어...
        return "index";
    }

    //지원 0223
    // 주문상세버튼을 누를 시 해당 주문으로 이동되는 페이지입니다.
    @GetMapping("/order/{orderNumber}")
    public String orderDetail(@PathVariable Long orderNumber, Model model) {
        System.out.println("StoreController.orderDetail");

        OrderDTO orderDTO = ss.findByOrder(orderNumber); //주문자의 정보가 함께 와야하지...
        List<OrderMenuDTO> orderMenuDTOList = ss.orderMenu(orderNumber);

        CustomerDetailDTO customerDetailDTO = cs.findById(orderDTO.getCustomerNumber());
        // 고객 주소도 필요하니까 ㅎㅎ...
        StoreDetailDTO storeDetailDTO = ss.findByNumber(orderDTO.getStoreNumber());
        // store에서 기능하기때문에 customer가 아닌 store로 진행.

        model.addAttribute("order", orderDTO);
        model.addAttribute("menu", orderMenuDTOList);
        model.addAttribute("storeDetail", storeDetailDTO);
        model.addAttribute("customer", customerDetailDTO);

        System.out.println(orderMenuDTOList);

        return "store/orderDetail";
    }


    //심기일전... 0227 n회차 재도전 findAll
    @GetMapping("/orderAll/{storeEmail}")
    public String orderFindAll(@PathVariable String storeEmail, Model model) {
        System.out.println("StoreController.orderAll");

        StoreDetailDTO storeDetailDTO = ss.findById(storeEmail);
        List<OrderDTO> orderAll = ss.findOrderAll(storeEmail);
        /*List<OrderDTO> storeDetailDTOList = ss.findOrderAll(storeDetailDTO.getStoreNumber());*/

        model.addAttribute("store", storeDetailDTO);
        model.addAttribute("orderAll", orderAll);

        System.out.println("store: " + storeDetailDTO);
        System.out.println("페이지 출력만이라도 일단 ㅠ");
        return "store/orderList";
    }

    //0302 라이더선택을 위한 창 띄우기...admin에 있네.


    @GetMapping("/riderList/{orderNumber}")
    public String riderList(@PathVariable Long orderNumber, Model model) {
        List<RiderDTO> riderList = as.riderFindAll();
        OrderDTO orderDTO = ss.findByOrder(orderNumber);
        model.addAttribute("customerEmail",orderDTO.getCutomerEmail());
        model.addAttribute("orderNumber", orderNumber);
        model.addAttribute("riderList", riderList);
        model.addAttribute("orderFinish", new OrderNowDTO());
        System.out.println(riderList);
        return "store/riderList";
    }

    //라이더 선택
    //라이더 선택 완료시
    @PostMapping("/rider")
    @ResponseBody
    public String riderSelect(@RequestParam("riderNumber") Long riderNumber,
                              @RequestParam("orderNumber") Long orderNumber) {
        String result = ss.riderStart(riderNumber,orderNumber);
        return result;
    }

    @PutMapping("/rider")
    @ResponseBody
    public String riderEnd(@RequestParam("orderNumber") Long orderNumber) {
        String result = ss.riderEnd(orderNumber);
        ss.dailySale(orderNumber);
        return result;
    }


    //아 증말 죄송합니다, 이건 메뉴리스트(업체용)이에요
    @GetMapping("/menuControl/{storeEmail}")
    public String storeMenuControl(@PathVariable String storeEmail, Model model) {
        // storeNumber들고 MenuList띄웁니다
        StoreDetailDTO storeDetailDTO = ss.findById(storeEmail);
        /*StoreDetailDTO storeDetailDTO = ss.findByNumber(storeNumber);*/
        List<MenuDTO> menuList = ss.menuFindAll(storeDetailDTO.getStoreNumber());
        model.addAttribute("store", storeDetailDTO);
        model.addAttribute("menuList", menuList);
        System.out.println("메뉴리스트 띄우기");
        return "store/menuControl";
    }

    @GetMapping("/sale/{storeEmail}")
    public String salePage(@PathVariable String storeEmail, Model model) {
        //판매 내역 출력
        StoreDetailDTO store = ss.findById(storeEmail);
        model.addAttribute("store", store);
        List<OrderDTO> order = ss.findOrderAll(storeEmail);
        model.addAttribute("order", order);
        List<DailySaleDTO> sale = ss.findSaleAll(store.getStoreNumber());
        model.addAttribute("sale", sale);
        System.out.println("판매내역 출력할 것");
        return "store/sale";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchType") String searchType,@RequestParam("keyword") String keyword,Model model){
        //
        List<StoreDetailDTO> storeDetailDTOS = ss.search(searchType,keyword);
        model.addAttribute("storeList",storeDetailDTOS);
        return "store/category";
    }

    @PostMapping("/wishList")
    @ResponseBody
    public String wishListAdd(@RequestParam("storeNumber") Long storeNumber) {
        // 회원 찜목록에 추가
        String  result = cs.wishlistAdd(storeNumber);
        return result;
    }
}
