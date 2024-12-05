package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.RefundMapper;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.pages.RefundService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
        settlementDto.setReqGubun("RQ");        // 요청구분(SE01) : AQ(환불요청)
        settlementDto.setApplyStatus("01");     // 승인여부(SE04) : 01(승인요청)
        return applypaymentService.saveApplypayment(settlementDto);
    }

    @DeleteMapping
    public int deleteRefund(@RequestBody SettlementDto settlementDto) {
        return applypaymentService.deleteSettlement(settlementDto);
    }
}
