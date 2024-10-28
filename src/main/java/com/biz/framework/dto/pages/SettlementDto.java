package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettlementDto extends BaseDto {

    private String settlementSeq;   //정산내역ID
    private String prodId;          //상품ID
    private String userId;          //직원ID
    private String custId;          //고객ID
    private String reqDate;         //정산요청일
    private String prodAmt;         //상품가
    private String saleAmt;         //판매가
    private String inflowCnt;       //유입수
    private String saleTotalAmt;    //판매총액
    private String reqGubun;        //요청구분
    private String depositAmt;      //입금예정금액
    private String depositRealAmt;  //실제입금액
    private String dateWorkFrom;    //작업시작일
    private String dateWorkTo;      //작업종료일
    private String workDay;         //작업일
    private String incentiveRate;   //직원인센티브율
    private String empPayment;      //직원수수료
    private String refundInd;       //환불여부
    private String atchId;          //첨부ID
    private String status;          //승인여부
    private String taxInd;          //세금계산서발행여부
    private String payYm;           //급여년월
}
