package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerDetailDTO;
import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
import com.et.eatingtogether.dto.store.StoreDetailDTO;
import com.et.eatingtogether.dto.store.StoreSaveDTO;
import com.et.eatingtogether.entity.BigCategoryEntity;
import com.et.eatingtogether.entity.CustomerEntity;
import com.et.eatingtogether.entity.StoreEntity;
import com.et.eatingtogether.repository.BigCategoryRepository;
import com.et.eatingtogether.repository.CustomerRepository;
import com.et.eatingtogether.repository.StoreRepository;
import com.et.eatingtogether.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
    private final CustomerRepository cr;
    private final StoreRepository sr;
    private final HttpSession session;
    private final BigCategoryRepository bcr;

    // 회원용 회원가입
    @Transactional
    public Long joinCustomer(CustomerSaveDTO customerSaveDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customerSaveDTO.setCustomerPassword(passwordEncoder.encode(customerSaveDTO.getCustomerPassword()));
        return cr.save(CustomerEntity.toCustomerSave(customerSaveDTO)).getCustomerNumber();
    }

    // 업체용 회원가입
    @Transactional
    public Long joinStore(StoreSaveDTO storeSaveDTO) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!storeSaveDTO.getStoreFile().isEmpty()) {
            MultipartFile storeFile = storeSaveDTO.getStoreFile();
            String storeFilename = storeFile.getOriginalFilename();
            storeFilename = System.currentTimeMillis() + "-" + storeFilename;
            System.out.println("파일이름: " + storeFilename);
//            String savePath = "C:\\Users\\wkddn\\Desktop\\wkddnjs\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename; //장원씨꺼
            String savePath = "C:\\development\\source\\FinalProject\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename;
//            String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename;
            if (!storeFile.isEmpty()) {
                storeFile.transferTo(new File(savePath));
            }
            storeSaveDTO.setStoreFilename(storeFilename);
        }
        BigCategoryEntity bigCategoryEntity = bcr.findByBigCategoryNumber(storeSaveDTO.getBigCategoryNumber());
        StoreEntity storeEntity = StoreEntity.toSaveStore(storeSaveDTO, bigCategoryEntity);
        storeEntity.setStorePassword(passwordEncoder.encode(storeSaveDTO.getStorePassword()));
        return sr.save(storeEntity).getStoreNumber();
    }

    // 로그인용
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("시큐리티 로그인");
        System.out.println("userEmail = " + userEmail);
        Optional<CustomerEntity> userEntityWrapper = cr.findByCustomerEmail(userEmail);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (!userEntityWrapper.isEmpty()) {
            System.out.println("회원");
            CustomerEntity userEntity = userEntityWrapper.get();


            if (("admin").equals(userEmail)){
                authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
            } else {
                authorities.add(new SimpleGrantedAuthority(Role.CUSTOMER.getValue()));
            }

            session.setAttribute("customerLoginEmail",userEntity.getCustomerEmail());

            return new User(userEntity.getCustomerEmail(),userEntity.getCustomerPassword(),authorities);
        } else {
            System.out.println("업체");
            StoreEntity storeEntity = sr.findByStoreEmail(userEmail);

            authorities.add(new SimpleGrantedAuthority(Role.STORE.getValue()));

            session.setAttribute("storeLoginEmail",storeEntity.getStoreEmail());

            return new User(storeEntity.getStoreEmail(),storeEntity.getStorePassword(),authorities);
        }
    }

    public String updateStore(StoreDetailDTO storeDetailDTO) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        BigCategoryEntity bigCategoryEntity = bcr.findByBigCategoryNumber(storeDetailDTO.getBigCategoryNumber());
        Optional<StoreEntity> storeEntity = sr.findById(storeDetailDTO.getStoreNumber());
        if (storeEntity.get().getStoreNumber().equals(storeDetailDTO.getStoreNumber())) {
            //number값이 일치한다면

                MultipartFile storeFile = storeDetailDTO.getStoreFile();
                String storeFilename = storeFile.getOriginalFilename();
                storeFilename = System.currentTimeMillis() + "-" + storeFilename;
                System.out.println("파일이름: " + storeFilename);
                String savePath = "C:\\development\\source\\FinalProject\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename;
//                String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename; // 지원이꺼
                /*            String savePath = "C:\\development_psy\\source\\springboot\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename; // 내꺼*/
                if (!storeFile.isEmpty()) {
                    storeFile.transferTo(new File(savePath));
                }
                storeDetailDTO.setStoreFilename(storeFilename);

            storeDetailDTO.setStorePassword(passwordEncoder.encode(storeDetailDTO.getStorePassword()));
            sr.save(StoreEntity.toUpdate(storeDetailDTO, bigCategoryEntity));
            System.out.println("수정합니당");
            return "ok";
        } else {
            System.out.println("수정못해");
            //number값이 일치하지 않는다면
            return "no";
        }
    }

    public String updateCustomer(CustomerDetailDTO customerDetailDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<CustomerEntity> customerEntity = cr.findById(customerDetailDTO.getCustomerNumber());
//        if (customerEntity.get().getCustomerPassword().equals(customerDetailDTO.getCustomerPassword())) {
//            // 비밀 번호가 일치하면
            customerDetailDTO.setCustomerPassword(passwordEncoder.encode(customerDetailDTO.getCustomerPassword()));
            cr.save(CustomerEntity.toUpdate(customerDetailDTO));
            return "ok";
            // 시큐리티에서 확인할 방법이 없으니 그냥 해줘야할듯
//        } else {
//            // 비밀 번호가 일치하지 않으면
//            return "no";
//        }
    }
}
