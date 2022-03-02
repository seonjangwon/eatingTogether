package com.et.eatingtogether.dto.customer;

import com.et.eatingtogether.entity.CustomerBlacklistEntity;
import com.et.eatingtogether.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBlacklistDTO {
    private Long customerBlacklistNumber; // 신고번호
    private Long customerNumber; // 신고당한 회원
    private String customerLoginEmail; // 신고한 회원 이메일
    private String reportCause; // 신고사유
    private String reportOpinion; // 기타의견


   public static CustomerBlacklistDTO toDTO(CustomerBlacklistEntity c){
        CustomerBlacklistDTO customerBlacklistDTO = new CustomerBlacklistDTO();
        customerBlacklistDTO.setCustomerBlacklistNumber(c.getCustomerBlacklistNumber());
        customerBlacklistDTO.setCustomerNumber(c.getCustomerEntity().getCustomerNumber());
        customerBlacklistDTO.setCustomerLoginEmail(c.getLoginCustomEmail());
        customerBlacklistDTO.setReportCause(c.getReportCause());
        customerBlacklistDTO.setReportOpinion(c.getReportOpinion());
        return customerBlacklistDTO;
    }
}
