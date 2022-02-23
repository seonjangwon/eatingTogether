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
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class CustomerUserService implements UserDetailsService {
    private final CustomerRepository cr;
    private final StoreRepository sr;
    private final BigCategoryRepository bcr;
    private final HttpSession session;

    @Transactional
    public Long joinUser(CustomerSaveDTO customerSaveDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        customerSaveDTO.setCustomerPassword(passwordEncoder.encode(customerSaveDTO.getCustomerPassword()));

        return cr.save(CustomerEntity.toCustomerSave(customerSaveDTO)).getCustomerNumber();
    }
    @Transactional
    public Long storeJoinUser(StoreSaveDTO storeSaveDTO) throws IOException {

        if (!storeSaveDTO.getStoreFile().isEmpty()) {
            MultipartFile storeFile = storeSaveDTO.getStoreFile();
            String storeFilename = storeFile.getOriginalFilename();
            storeFilename = System.currentTimeMillis() + "-" + storeFilename;
            System.out.println("파일이름: " + storeFilename);
            String savePath = "C:\\Users\\exo_g\\Documents\\GitHub\\eatingTogether\\src\\main\\resources\\static\\upload\\" + storeFilename;
            if (!storeFile.isEmpty()) {
                storeFile.transferTo(new File(savePath));
            }
            storeSaveDTO.setStoreFilename(storeFilename);
        }

        BigCategoryEntity bigCategoryEntity = bcr.findByBigCategoryNumber(storeSaveDTO.getBigCategoryNumber());
        StoreEntity storeEntity = StoreEntity.toSaveStore(storeSaveDTO, bigCategoryEntity);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        storeEntity.setStorePassword(passwordEncoder.encode(storeSaveDTO.getStorePassword()));

        return sr.save(storeEntity).getStoreNumber();
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<CustomerEntity> userEntityWrapper = cr.findByCustomerEmail(userEmail);
        CustomerEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin").equals(userEmail)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.CUSTOMER.getValue()));
        }

        session.setAttribute("customerLoginEmail",userEntity.getCustomerEmail());

        return new User(userEntity.getCustomerEmail(),userEntity.getCustomerPassword(),authorities);
    }
}
