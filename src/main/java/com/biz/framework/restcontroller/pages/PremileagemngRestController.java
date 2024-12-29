package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.service.pages.ApplypaymentapprmngService;
import com.biz.framework.service.pages.PremileagemngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/premileagemng")
@RequiredArgsConstructor
public class PremileagemngRestController {

    private final ApplypaymentapprmngService applypaymentapprmngService;
    private final PremileagemngService premileagemngService;

    @GetMapping
    public List<CamelCaseMap> findPremileagemngList(MileageReqDto mileageReqDto) {
        return premileagemngService.findPremileagemngList(mileageReqDto);
    }

    /* 요청 승인 */
    @PostMapping
    public int confirmMileageReq(@RequestBody MileageReqDto mileageReqDto) {
        return premileagemngService.confirmMileageReq(mileageReqDto);
    }

    @PostMapping("/cancel")
    public int cancelMileageReq(@RequestBody MileageReqDto mileageReqDto) {
        return premileagemngService.cancelMileageReq(mileageReqDto);
    }
}
