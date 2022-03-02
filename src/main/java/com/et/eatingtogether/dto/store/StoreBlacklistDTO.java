package com.et.eatingtogether.dto.store;

import com.et.eatingtogether.dto.customer.CustomerBlacklistDTO;
import com.et.eatingtogether.entity.CustomerBlacklistEntity;
import com.et.eatingtogether.entity.StoreBlacklistEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreBlacklistDTO {
    private Long storeBlacklistNumber; // 신고번호
    private Long storeNumber; // 신고당한 회원
    private String customerLoginEmail; // 신고한 회원 이메일
    private String reportCause; // 신고사유
    private String reportOpinion; // 기타의견


    public static StoreBlacklistDTO toDTO(StoreBlacklistEntity c){
        StoreBlacklistDTO storeBlacklistDTO = new StoreBlacklistDTO();
        storeBlacklistDTO.setStoreBlacklistNumber(c.getStoreBlacklistNumber());
        storeBlacklistDTO.setStoreNumber(c.getStoreEntity().getStoreNumber());
        storeBlacklistDTO.setCustomerLoginEmail(c.getLoginCustomEmail());
        storeBlacklistDTO.setReportCause(c.getReportCause());
        storeBlacklistDTO.setReportOpinion(c.getReportOpinion());
        return storeBlacklistDTO;
    }


}
