package com.et.eatingtogether.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSaveDTO {

    public String storeEmail;
    public String storePassword;

    private MultipartFile storeFile;
    private String storeFilename;
    private String storeName;
    private String storePhone;
    private String storeOpen;
    private String storeClose;
    private String storeAddress;
}
