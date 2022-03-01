package com.et.eatingtogether.dto.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReportSaveDTO {
    private Long customerReportNumber; // 신고번호
    private Long customerNumber; // 신고당한 회원
    private String customerLoginEmail; // 신고한 회원 이메일
    private String[] reportCause; // 신고사유
    private String reportOpinion; // 기타의견


}
