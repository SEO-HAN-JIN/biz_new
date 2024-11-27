package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/refund")
@RequiredArgsConstructor
public class RefundRestController {

    private final EmpService empService;
    private final ApplypaymentService applypaymentService;

    @GetMapping("/payment/list")
    public List<CamelCaseMap> findPayemntList(SettlementDto settlementDto) {
        settlementDto.setStatus("2"); // 승인완료
        settlementDto.setUserId(settlementDto.getLoginUserId());
        settlementDto.setRefundInd("Y");
        return applypaymentService.findSettlement(settlementDto);
    }

    @GetMapping("/emps")
    public List<CamelCaseMap> findEmps() {
        return empService.findAllEmps();
    }

    @GetMapping
    public List<CamelCaseMap> findCustomers(SettlementDto settlementDto) {
        return applypaymentService.findApplypayment(settlementDto);
    }

    @PostMapping
    public int saveCustomer(@RequestBody SettlementDto settlementDto) {
        return applypaymentService.saveApplypayment(settlementDto);
    }

    @DeleteMapping
    public int deleteCustomer(@RequestBody CustomerDto customerDto) {
        return applypaymentService.deleteCustomer(customerDto);
    }
}
