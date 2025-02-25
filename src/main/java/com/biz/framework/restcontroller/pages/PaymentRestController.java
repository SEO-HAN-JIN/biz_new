package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.service.pages.ApplypaymentapprmngService;
import com.biz.framework.service.pages.PaymentService;
import com.biz.framework.service.pages.PremileagemngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/payment")
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;

    @GetMapping
    public List<CamelCaseMap> findPaymentList(SettlementmstDto settlementmstDto) {
        return paymentService.findPaymentList(settlementmstDto);
    }

    @GetMapping("/info")
    public List<CamelCaseMap> findPaymentInfoList(SettlementmstDto settlementmstDto) {
        return paymentService.findPaymentInfoList(settlementmstDto);
    }

    @GetMapping("/etc")
    public List<CamelCaseMap> findPaymentEtcList(SettlementmstDto settlementmstDto) {
        return paymentService.findPaymentEtcList(settlementmstDto);
    }

    @PostMapping
    public int savePayment(@RequestBody List<SettlementmstDto> settlementmstDto) {
        return paymentService.savePayment(settlementmstDto);
    }

    @DeleteMapping
    public int removePayment(@RequestBody List<SettlementmstDto> settlementmstDto) {
        return paymentService.removePayment(settlementmstDto);
    }

//    /* 요청 승인 */
//    @PostMapping
//    public int confirmMileageReq(@RequestBody MileageReqDto mileageReqDto) {
//        return premileagemngService.confirmMileageReq(mileageReqDto);
//    }
//
//    @PostMapping("/cancel")
//    public int cancelMileageReq(@RequestBody MileageReqDto mileageReqDto) {
//        return premileagemngService.cancelMileageReq(mileageReqDto);
//    }
}
