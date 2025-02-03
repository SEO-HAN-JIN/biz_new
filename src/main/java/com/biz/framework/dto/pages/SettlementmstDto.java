package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementmstDto extends BaseDto {

    private String confirmSeq;      // 정산완료ID
    private String settlementSeq;   // 정산내역ID
    private String custId;          // 고객ID
    private String userId;          // 직원ID
    private String confirmAmt;      // 확정입금액
    private String confirmMileage;  // 마일리지적립금액
    private String confirmRateAmt;  // 정산확정수수료
    private String confirmDate;     // 승인일자
    private String confirmInd;      // 승인여부
    private String taxInd;          // 세금계산서발행여부
    private String payYm;           // 급여년월
    private String reqGubun;        // 요청구분
    private String atchId;          // 첨부ID
    private String remark;

    private String searchPayYmStart;
    private String searchPayYmEnd;
    private String searchReqGubun;
    private String searchPayGubun;

    private String empName;
    private String custName;
    private String prodName;

    
    private List<SettlementmstDto> settlementmstDtoList;
    private String[] reqGubunList;
    private String loginCoId;

}
