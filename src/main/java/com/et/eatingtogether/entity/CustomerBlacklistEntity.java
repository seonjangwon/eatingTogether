package com.et.eatingtogether.entity;

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
    private Long customerBlacklistNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private CustomerEntity customerEntity;

//    // 블랙리스트 회원 : 신고 = 1:many
//    @OneToMany(mappedBy = "customerBlackListEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<CustomerReportEntity> customerReportEntityList = new ArrayList<>();

    private int customerBlacklistCount; // 신고당한 횟수



}
