package com.et.eatingtogether.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="storeReport_table")
public class StoreReportEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="storeReport_number") // 업체신고번호
        private Long storeReportNumber;

        @Column(name="report_cause") // 신고사유
        private String report_cause;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="storeNumber") //
        private StoreEntity storeEntity;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="storeBlackListNumber")
        private StoreBlacklistEntity storeBlacklistEntity;
}
