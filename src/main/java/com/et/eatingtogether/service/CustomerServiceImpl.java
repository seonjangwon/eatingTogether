package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.*;
import com.et.eatingtogether.dto.review.ReplyDetailDTO;
import com.et.eatingtogether.dto.review.ReviewDetailDTO;
import com.et.eatingtogether.dto.review.ReviewFileDTO;
import com.et.eatingtogether.dto.store.MenuDTO;
import com.et.eatingtogether.dto.system.CouponDTO;
import com.et.eatingtogether.dto.system.OrderDTO;
import com.et.eatingtogether.dto.system.OrderMenuDTO;
import com.et.eatingtogether.dto.system.OrderNowDTO;
import com.et.eatingtogether.entity.*;
import com.et.eatingtogether.repository.*;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.repository.CustomerRepository;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository cr;
    private final StoreRepository sr;
    private final ReplyRepository rpr;
    private final DeliveryRepository dr;
    private final BasketRepository br;
    private final PointRepository pr;
    private final OrderRepository or;
    private final OrderNowRepository onr;
    private final OrderMenuRepository omr;
    private final HttpSession session;

    @Override
    public void login(CustomerDetailDTO customerDetailDTO) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail(customerDetailDTO.getCustomerEmail());
        if (customerEntity.isPresent()) { // 있으면
            if (customerEntity.get().getCustomerPassword().equals(customerDetailDTO.getCustomerPassword())) { // 일치하면
                System.out.println("로그인");
                session.setAttribute("customerLoginEmail", customerEntity.get().getCustomerEmail());
            } else { // 틀리면
                System.out.println("비밀번호");
                throw new IllegalStateException("이메일 또는 비밀번호가 틀립니다");
            }
        } else { // 없으면
            System.out.println("이메일");
            throw new IllegalStateException("이메일 또는 비밀번호가 틀립니다");
        }

    }

    @Override
    public CustomerDetailDTO findByEmail(String customerLoginEmail) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail(customerLoginEmail);
        return CustomerDetailDTO.toEntity(customerEntity.get());
    }

    @Override
    public String update(CustomerDetailDTO customerDetailDTO) {
        Optional<CustomerEntity> customerEntity = cr.findById(customerDetailDTO.getCustomerNumber());
        if (customerEntity.get().getCustomerPassword().equals(customerDetailDTO.getCustomerPassword())) {
            // 비밀 번호가 일치하면
            cr.save(CustomerEntity.toUpdate(customerDetailDTO));
            return "ok";
        } else {
            // 비밀 번호가 일치하지 않으면
            return "no";
        }
    }


    @Override
    public List<OrderDTO> orderList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<OrderEntity> orderEntityList = customerEntity.get().getOrderEntityList();

        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity e : orderEntityList) {
            orderDTOList.add(OrderDTO.toEntity(e));
        }
        return orderDTOList;
    }

    @Override
    public OrderDTO findOrder(Long orderNumber) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<OrderEntity> orderEntityList = customerEntity.get().getOrderEntityList();
        for (OrderEntity o : orderEntityList) {
            if (o.getOrderNumber().equals(orderNumber)) {
                return OrderDTO.toEntity(o);
            }
        }
        return null;
    }

    @Override
    public List<OrderMenuDTO> orderMenu(Long orderNumber) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<OrderEntity> orderEntityList = customerEntity.get().getOrderEntityList();
        for (OrderEntity o : orderEntityList) {
            if (o.getOrderNumber().equals(orderNumber)) {
                List<OrderMenuEntity> orderMenuEntityList = o.getOrderMenuEntityList();
                List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
                for (OrderMenuEntity m : orderMenuEntityList) {
                    orderMenuDTOList.add(OrderMenuDTO.toEntity(m));
                }
                return orderMenuDTOList;
            }
        }
        return null;
    }

    @Override
    public CustomerDetailDTO findById(Long customerNumber) {
        return CustomerDetailDTO.toEntity(cr.findById(customerNumber).get());
    }

    @Override
    public List<MyCouponDTO> couponList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<MyCouponDTO> couponDTOList = new ArrayList<>();
        if (!customerEntity.get().getMyCouponEntityList().isEmpty()) {
            for (MyCouponEntity m : customerEntity.get().getMyCouponEntityList()) {
                couponDTOList.add(MyCouponDTO.toEntity(m));
            }
        }
        return couponDTOList;
    }

    @Override
    public List<PointDTO> pointList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<PointDTO> pointDTOList = new ArrayList<>();
        for (PointEntity p : customerEntity.get().getPointEntityList()) {
            pointDTOList.add(PointDTO.toEntity(p));
        }
        return pointDTOList;
    }

    @Override
    public List<ReviewDetailDTO> reviewList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<ReviewDetailDTO> reviewDetailDTOList = new ArrayList<>();
        for (ReviewEntity r : customerEntity.get().getReviewEntityList()) {
            reviewDetailDTOList.add(ReviewDetailDTO.toEntity(r));
        }
        for (ReviewDetailDTO r : reviewDetailDTOList) {
            r.setReplyDetailDTO(ReplyDetailDTO.toEntity(rpr.findById(r.getReviewNumber()).get()));
            for (ReviewFileEntity rf : r.getReviewFileEntityList()) {
                r.getReviewFileDTOList().add(ReviewFileDTO.toEntity(rf));
            }
        }
        return reviewDetailDTOList;
    }

    @Override
    public List<WishlistDTO> wishlist() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<WishlistDTO> wishlistDTOList = new ArrayList<>();
        for (WishlistEntity w : customerEntity.get().getWishlistEntity()) {
            wishlistDTOList.add(WishlistDTO.toEntity(w));
        }
        return wishlistDTOList;
    }

    @Override
    public List<BasketDTO> basketList() {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        List<BasketDTO> basketDTOList = new ArrayList<>();
        for (BasketEntity b : customerEntity.get().getBasketEntityList()) {
            basketDTOList.add(BasketDTO.toEntity(b));
        }
        return basketDTOList;
    }

    @Override
    public int deliveryPrice(Long storeNumber) {
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        Optional<DeliveryEntity> deliveryEntity = dr.findByStoreEntityAndDeliveryDname(sr.findById(storeNumber).get(), customerEntity.get().getCustomerDname());
//        Optional<DeliveryEntity> deliveryEntity = dr.findByStoreNumberAndDeliveryDname(storeNumber, customerEntity.get().getCustomerDname());
//        Optional<DeliveryEntity> deliveryEntity = dr.findByStoreEntityAndDeliveryDname(storeNumber, customerEntity.get().getCustomerDname());

        return deliveryEntity.get().getDeliveryPrice();
    }

    @Override
    public String menuUpDown(Long basketNumber, String type) {
        Optional<BasketEntity> basketEntity = br.findById(basketNumber);
        if (type.equals("up")) {
            basketEntity.get().setBasketMenuCount(basketEntity.get().getBasketMenuCount() + 1);
            br.save(basketEntity.get());
        } else {
            basketEntity.get().setBasketMenuCount(basketEntity.get().getBasketMenuCount() - 1);
            br.save(basketEntity.get());
        }
        return "ok";
    }

    @Override
    public void menuDelete(Long basketNumber) {
        br.deleteById(basketNumber);
    }

    // 회원가입
    @Override
    public Long save(CustomerSaveDTO customerSaveDTO) {
        // dto -> entity로 변환

        return cr.save(CustomerEntity.toCustomerSave(customerSaveDTO)).getCustomerNumber();

    }

    @Override
    public String findByCustomerEmail(String customerEmail) {
        System.out.println("CustomerServiceImpl.findByCustomerEmail");
//       cr.findByCustomerEmail(customerEmail);
        if (cr.findByCustomerEmail(customerEmail).isEmpty()) {
            return "ok";
        } else {
            return "no";
        }


    }

    @Override
    public void pointUse(int pointUse, Long orderNumber) {
        System.out.println("CustomerServiceImpl.pointUse");
        // 포인트 내역 추가 하고 포인트 깍고
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        OrderEntity orderEntity = or.findById(orderNumber).get();

        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointQuantity(pointUse);
        pointDTO.setPointHistory("사용");
        pointDTO.setPointTime(LocalDateTime.now());

        pr.save(PointEntity.toDTO(pointDTO, customerEntity.get(), orderEntity));
        customerEntity.get().setCustomerPoint(customerEntity.get().getCustomerPoint() - pointUse);
        cr.save(customerEntity.get());
    }

    @Override
    public void pointAdd(Long orderNumber) {
        System.out.println("CustomerServiceImpl.pointAdd");
        // 포인트적립
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        OrderEntity orderEntity = or.findById(orderNumber).get();

        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointQuantity(orderEntity.getOrderPrice() / 100);
        pointDTO.setPointHistory("적립");
        pointDTO.setPointTime(LocalDateTime.now());

        customerEntity.get().setCustomerPoint(customerEntity.get().getCustomerPoint() + (orderEntity.getOrderPrice() / 100));
        pr.save(PointEntity.toDTO(pointDTO, customerEntity.get(), orderEntity));
        cr.save(customerEntity.get());
    }

    @Override
    public Long orderSave(OrderDTO orderDTO) {
        System.out.println("CustomerServiceImpl.orderSave");
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        StoreEntity storeEntity = sr.findById(orderDTO.getStoreNumber()).get();
        // 주문내역 저장
        Long orderNumber = or.save(OrderEntity.toDTO(orderDTO, customerEntity.get(), storeEntity)).getOrderNumber();
        // 주문 상황 만들고 저장
        Optional<DeliveryEntity> deliveryEntity = dr.findByStoreEntityAndDeliveryDname(sr.findById(orderDTO.getStoreNumber()).get(), customerEntity.get().getCustomerDname());
        OrderEntity orderEntity = or.findById(orderNumber).get();
        OrderNowDTO orderNowDTO = new OrderNowDTO();
        orderNowDTO.setOrderNowStatus("접수 대기");
        orderNowDTO.setOrderNowTime(LocalDateTime.now().minusMinutes(deliveryEntity.get().getDeliveryTime()));
        onr.save(OrderNowEntity.toDTO(orderNowDTO, orderEntity));
        // 주문 메뉴 저장
        for (BasketEntity b : customerEntity.get().getBasketEntityList()) {
            omr.save(OrderMenuEntity.toDTO(b, orderEntity));
        }
        return orderNumber;
    }

    @Override
    public void basketDeleteAll() {
        System.out.println("CustomerServiceImpl.basketDeleteAll");
        Optional<CustomerEntity> customerEntity = cr.findByCustomerEmail((String) session.getAttribute("customerLoginEmail"));
        for (BasketEntity b : customerEntity.get().getBasketEntityList()) {
            br.deleteById(b.getBasketNumber());
        }
        // 장바구니 모두 삭제
    }

    @Override
    public String getAccessToken(String code) {
        String access_token = "";
        String refresh_token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // post 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=b577db5cfc34ee9556c27e55b2f22763");
            sb.append("&redirect_uri=http://localhost:8099/customer/kakaoLogin");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            // 200이면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            // 요청을 통해 얻은 JSON타입의 response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);
            // gson 라이브러리에 포함된 클래스로 json파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            access_token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_token = element.getAsJsonObject().get("refresh_token").getAsString();
            // 이건 왜 하는거지 refresh 쓰는 곳이 없는데?
            System.out.println("access_token = " + access_token);
            System.out.println("refresh_token = " + refresh_token);
            br.close();
            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return access_token;
    }

    @Override
    public HashMap<String, String> getUserInfo(String access_token) {
        HashMap<String,String> userInfo = new HashMap<String,String>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer "+ access_token);
            // 200이면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null){
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            String id = element.getAsJsonObject().get("id").getAsString();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            userInfo.put("id",id);
            userInfo.put("nickname",nickname);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    @Override
    public void kakaoLogout(String access_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection)   url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer "+ access_token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = "";
            String line = "";
            while ((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("result = " + result);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void kakaoUnlink(String access_token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection)   url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer "+ access_token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = "";
            String line = "";
            while ((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("result = " + result);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
