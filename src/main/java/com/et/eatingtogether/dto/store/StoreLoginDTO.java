package com.et.eatingtogether.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreLoginDTO {

    private String storeEmail;
    private String storePassword;

}
