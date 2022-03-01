package com.et.eatingtogether.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="customerReport_table")
public class CustomerReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cusotomerReport_number")
    private Long customerReportNumber; // 회원신고번호(pk)

    @Column(name="report_cause")
    private String[] reportCause; // 사유는 체크박스로 여러개 선택 -> 배열로 저장

    @Column
    private String reportOpinion; // 기타의견

    @Column
    private String loginCustomEmail; // 신고하는 회원?

    @Column
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customerNumber")
    private CustomerEntity customerEntity; // 신고당하는 회원?

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="customerBlackListNumber")
//    private CustomerBlacklistEntity customerBlacklistEntity;



}
