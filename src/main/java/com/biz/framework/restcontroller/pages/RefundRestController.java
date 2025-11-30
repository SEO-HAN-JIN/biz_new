package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/refund")
@RequiredArgsConstructor
public class RefundRestController {

    private final ApplypaymentService applypaymentService;
    private final RefundService refundService;

    @GetMapping("/payment/list")
    public List<CamelCaseMap> findPayemntList(SettlementDto settlementDto) {
        return refundService.findSettlement(settlementDto);
    }

    @GetMapping
    public List<CamelCaseMap> findRefund(SettlementDto settlementDto) {
        return refundService.findRefund(settlementDto);
    }

    @PostMapping
    public int saveApplypayment(@RequestBody SettlementDto settlementDto) {
        settlementDto.setRefundInd("Y");
        settlementDto.setReqGubun("RQ");        // 요청구분(SE01) : RQ(환불요청)
        settlementDto.setApplyStatus("01");     // 승인상태(SE04) : 01(승인요청)
        settlementDto.setRefundSettlementSeq(settlementDto.getSettlementSeq());
        return refundService.saveRefund(settlementDto);
    }

    @DeleteMapping
    public int deleteRefund(@RequestBody SettlementDto settlementDto) {
        return refundService.deleteRefund(settlementDto);
    }

    @GetMapping("/prodItem/list/{settlementSeq}/{prodId}")
    public List<SettlementDto.TbSettlementProdItemDto> findProductItemBySettlementSeq(@PathVariable String settlementSeq, @PathVariable String prodId) {
        SettlementDto.TbSettlementProdItemDto tbSettlementProdItemDto = new SettlementDto.TbSettlementProdItemDto();
        tbSettlementProdItemDto.setSettlementSeq(settlementSeq);
        tbSettlementProdItemDto.setProdId(prodId);
        return refundService.findProductItemListBySettlementSeqAndProdId(tbSettlementProdItemDto);

    }

    @GetMapping("/refund-items")
    public List<CamelCaseMap> findRefundItems(SettlementDto settlementDto) {
        return refundService.findRefundItems(settlementDto);
    }

    @GetMapping("/refund-items/{settlementSeq}")
    public List<CamelCaseMap> findRefundItemsBySettlementSeq(@PathVariable String settlementSeq) {
        SettlementDto.TbSettlementRefundItemDto tbSettlementRefundItemDto = new SettlementDto.TbSettlementRefundItemDto();
        tbSettlementRefundItemDto.setSettlementSeq(settlementSeq);
        return refundService.findRefundItemsBySettlementSeq(tbSettlementRefundItemDto);
    }
}
