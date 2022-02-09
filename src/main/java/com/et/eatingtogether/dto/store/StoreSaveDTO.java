package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.BigCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSaveDTO {

    private Long bigCategoryNumber;

    private String storeEmail;
    private String storePassword;

    private MultipartFile storeFile;
    private String storeFilename;
    private String storeName;
    private String storePhone;
    private String storeOpen;
    private String storeClose;
    private String storeAddress;
}
