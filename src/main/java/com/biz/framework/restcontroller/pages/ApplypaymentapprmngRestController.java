package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.file.FileDto;
import com.biz.framework.file.FileService;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.ApplypaymentapprmngService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/applypaymentapprmng")
@RequiredArgsConstructor
public class ApplypaymentapprmngRestController {

    private final ApplypaymentapprmngService applypaymentapprmngService;
    private final FileService fileService;

    @GetMapping
    public List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto) {
        return applypaymentapprmngService.findApplypaymentmngList(settlementDto);
    }

    /* 요청 승인 */
    @PostMapping
    public int confirmApplypayment(@RequestBody SettlementmstDto settlementmstDto) {
        return applypaymentapprmngService.confirmApplypayment(settlementmstDto);
    }

    @PostMapping("/cancel")
    public int cancelApplypayment(@RequestBody SettlementmstDto settlementmstDto) {
        return applypaymentapprmngService.cancelApplypayment(settlementmstDto);
    }

    @PostMapping("/delete")
    public int deleteApplypayment(@RequestBody SettlementmstDto settlementmstDto) {
        return applypaymentapprmngService.deleteApplypayment(settlementmstDto);
    }

    @GetMapping("/files")
    public List<FileDto> findFileList(@RequestParam(value = "atchFileId") String atchFileId) {
        return fileService.findByFileId(atchFileId);
    }
}
