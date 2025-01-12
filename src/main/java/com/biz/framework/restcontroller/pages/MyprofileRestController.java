package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.MyprofileService;
import com.biz.framework.service.pages.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/myprofile")
@RequiredArgsConstructor
public class MyprofileRestController {

    private final MyprofileService myprofileService;

    @GetMapping
    public CamelCaseMap findMyprofile(EmpDto empDto) {
        return myprofileService.findMyprofile(empDto);
    }

    @PostMapping("/info")
    public int saveMyprofile(@RequestBody EmpDto empDto) {
        return myprofileService.saveMyprofile(empDto);
    }

    @PostMapping("/pwd")
    public int changePassword(@RequestBody EmpDto empDto) {
        return myprofileService.changePassword(empDto);
    }
}
