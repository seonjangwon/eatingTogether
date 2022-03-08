package com.et.eatingtogether.service;

import com.et.eatingtogether.dto.customer.CustomerSaveDTO;
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
            /*String savePath = "C:\\Users\\wkddn\\Desktop\\wkddnjs\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename;*/ //장원씨꺼
            String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\store\\" + storeFilename;
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
}
