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
    private String expectAmt;       // 입금예정금액(환불금액)
    private String expectRateAmt;   // 예정수수료(환불수수료)
    private String dateWorkFrom;    // 작업시작일
    private String dateWorkTo;      // 작업종료일
    private String workDay;         // 작업일
    private String incentiveRate;   // 직원인센티브율
    private String applyGubun;      // (정산)요청구분(신규,연장)
    private String mileageUseInd;   // (정산)마일리지 사용여부
    private String useMileage;      // (정산)마일리지 사용 금액
    private String saveMileage;     // (정산,환불)마일리지 적립 금액
    private String refundInd;       // (환불)여부
    private String refundSettlementSeq; //(환불)요청시 정산내역ID
    private String refundGubun;     // (환불)요청구분(전액환불,환불킵)
    private String confirmAmt;      // (승인)실제 확정 입금액
    private String confirmRateAmt;  // (승인)정산확정수수료
    private String applyStatus;     // (승인)승인여부
    private String taxInd;          // (승인)세금계산서발행여부
    private String payYm;           // (승인)급여년월
    private String reqGubun;        // 요청구분
    private String atchId;          // 첨부ID
    private String curUseMileage;
    private String refundDate;

    private String searchStartDate;
    private String searchEndDate;
    private String empName;
    private String custName;
    private String prodName;

    private List<SettlementDto> settlementDtoList;

}
