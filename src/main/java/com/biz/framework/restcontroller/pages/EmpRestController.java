package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/emp")
@RequiredArgsConstructor
public class EmpRestController {

    private final CustomerService customerService;
    private final EmpService empService;

    @GetMapping
    public List<CamelCaseMap> findEmps(EmpDto empDto) {
        return empService.findEmps(empDto);
    }

    @PostMapping
    public int saveEmp(@RequestBody EmpDto empDto) {
        return empService.saveEmp(empDto);
    }

    @DeleteMapping
    public int deleteEmp(@RequestBody EmpDto empDto) {
        return empService.deleteEmp(empDto);
    }


    /**
     * choice selectbox 리스트 가져오기
     * @param empDto
     * @return
     */
    @GetMapping("/select")
    public List<CamelCaseMap> findEmpSelect(EmpDto empDto) {
        return empService.findEmpSelect(empDto);
    }
}
