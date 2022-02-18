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
    private Long customerReportNumber;

    @Column(name="report_cause")
    private String reportCause;

    @Column
    private String reportOpinion;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="customerNumber")
//    private CustomerEntity customerEntity;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="customerBlackListNumber")
//    private CustomerBlacklistEntity customerBlacklistEntity;



}
