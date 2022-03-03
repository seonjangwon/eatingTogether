package com.et.eatingtogether.entity;

import com.et.eatingtogether.dto.customer.CustomerBlacklistDTO;
import com.et.eatingtogether.dto.system.ReportSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customerBlacklist_table")
public class CustomerBlacklistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerBlacklist_number")
    private Long customerBlacklistNumber; // 블랙리스트 회원번호(pk)

    @Column
    private String reportCause; // 사유는 체크박스로 여러개 선택 -> 배열로 저장?

    @Column
    private String reportOpinion; // 기타의견

    @Column
    private String loginCustomEmail; // 신고하는 회원?

//    @Column
// private int reportCount; // 신고횟수
    // 1.테이블들 따로 만든다
    // 번호, 신고당한애 => 테이블 => 여기에 CustomerBalckListEntity 를 참조함
    // List의 사이즈를 봄


    // 신고당하는 회원의 신고횟수는 여러번 가능, 회원은 1명.


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity; // 신고당하는 회원?

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="customerBlackListNumber")
//    private CustomerBlacklistEntity customerBlacklistEntity;

    public static CustomerBlacklistEntity toEntity (CustomerBlacklistDTO reportSaveDTO, CustomerEntity customerEntity){
        CustomerBlacklistEntity customerBlacklistEntity = new CustomerBlacklistEntity();
        customerBlacklistEntity.setCustomerEntity(customerEntity);
        customerBlacklistEntity.setReportCause(reportSaveDTO.getReportCause());
        customerBlacklistEntity.setReportOpinion(reportSaveDTO.getReportOpinion());
        customerBlacklistEntity.setLoginCustomEmail(reportSaveDTO.getCustomerLoginEmail());
        return customerBlacklistEntity;

    }





}
