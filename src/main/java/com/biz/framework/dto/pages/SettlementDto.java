package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementDto extends BaseDto {

    private String settlementSeq;   // 정산내역ID
    private String prodId;          // 상품ID
    private String userId;          // 직원ID
    private String custId;          // 고객ID
    private String reqDate;         // 요청일
    private String prodAmt;         // 상품가
    private String saleAmt;         // 판매가
    private String inflowCnt;       // 유입수
    private String saleTotalAmt;    // 판매총액
    private String prodTotalAmt;    // 상품가액
    private String expectAmt;       // 입금예정금액(환불금액)
    private String expectRateAmt;   // 예정수수료(환불수수료)
    private String dateWorkFrom;    // 작업시작일
    private String dateWorkTo;      // 작업종료일
    private String workDay;         // 작업일
    private String incentiveRate;   // 직원인센티브율
    private String mileageUseInd;   // (정산)마일리지 사용여부
    private String useMileage;      // (정산)마일리지 사용 금액
    private String existMileage;     // 정산_기존마일리지
    private String refundInd;       // (환불)여부
    private String refundDate;      // 환불_요청일
    private String refundWorkDay;
    private String refundInflowCnt;
    private String refundSaleTotalAmt;
    private String refundProdTotalAmt;
    private String refundSettlementSeq; //(환불)요청시 정산내역ID
    private String refundExpectAmt;
    private String refundExpectRateAmt;
    private String gubun;
    private String applyStatus;
    private String reqGubun;        // 요청구분

    private String searchStartDate;
    private String searchEndDate;
    private String emplName;
    private String custName;
    private String prodName;
    private String gubunName;

    private List<SettlementDto> settlementDtoList;

}
