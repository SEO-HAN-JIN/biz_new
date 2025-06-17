package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaxinvoicesDto extends BaseDto {

    private String confirmSeq;      // 정산완료ID
    private String settlementSeq;   // 정산내역ID
    private String custName;        // 고객명
    private String bizNo;           // 고객 사업자번호
    private String ownerName;       // 대표자명
    private String custMail;        // 대표이메일
    private String custTel;         // 대표연락처
    private String custAddr;        // 대표주소
    private String userId;          // 직원ID
    private String confirmAmt;      // 확정입금액
    private String confirmDate;     // 승인일자
    private String supplyAmount;    // 공급가액
    private String taxAmount;       // 세액
    private String totalAmount;     // 합계액
    private String taxInd;          // 세금계산서발행여부
    private String invoiceNo;       // 세금계산서번호
    private String issueDate;       // 발행일
    private String status;          // 발행상태
    private String bizType;         // 업태
    private String bizClass;        // 종목

    private String taxKey;          // 세금계산서 key

    private String remark;

    private String searchStartDate;
    private String searchEndDate;
    private String searchCustName;

    private List<TaxinvoicesDto> taxinvoicesDtoList;

    private List<TaxinvoiceslineDto> taxinvoiceslineDtoList;

}
