package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.entity.BigCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreSaveDTO {

    private Long bigCategoryNumber;

    @NotBlank(message = "필수기재 사항입니다")
    private String storeEmail;
    @NotBlank(message = "필수기재 사항입니다")
    private String storePassword;

    private MultipartFile storeFile;
    private String storeFilename;

    @NotBlank(message = "필수기재 사항입니다")
    private String storeName;
    private String storePhone;
    private String storeOpen;
    private String storeClose;
    private String storeAddress;



    private String storeName;
}
