package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettlementDto extends BaseDto {

    private String settlementSeq;       //정산내역ID
    private String prodId;              //상품ID
    private String userId;              //직원ID
    private String custId;              //고객ID
    private String reqDate;             //정산요청일
    private String reqGubun;            //상품가
    private String prodAmt;             //판매가
    private String saleAmt;             //유입수
    private String inflowCnt;           //판매총액
    private String saleTotalAmt;        //요청구분
    private String expectAmt;           //입금예정금액
    private String expectRateAmt;       //정산예정수수료
    private String mileageUseInd;       //입금예정금액
    private String useMileage;          //정산예정수수료
    private String saveMileage;         //사용마일리지
    private String dateWorkFrom;        //적립마일리지
    private String dateWorkTo;          //작업시작일
    private String workDay;             //작업종료일
    private String incentiveRate;       //작업일
    private String refundInd;           //직원인센티브율
    private String refundSettlementSeq; //첨부ID
    private String refundGubun;         //승인여부
    private String atchId;              //세금계산서발행여부
    private String confirmAmt;          //급여년월
    private String confirmRateAmt;      //환불여부
    private String status;              // 환불요청시 정산내역ID
    private String taxInd;             // 환불요청구분
    private String payYm;

    private String searchStartDate;
    private String searchEndDate;
    private String empName;
    private String custName;
    private String prodName;

    private List<SettlementDto> settlementDtoList;

}
