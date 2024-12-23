package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.PreMileageReqService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/premileagereq")
@RequiredArgsConstructor
public class PreMileageReqRestController {

    private final PreMileageReqService preMileageReqService;

    @GetMapping
    public List<CamelCaseMap> findPreMileageReq(MileageReqDto mileageReqDto) {
        return preMileageReqService.findPreMileageReq(mileageReqDto);
    }

    @PostMapping
    public int savePreMileageReq(@RequestBody MileageReqDto mileageReqDto) {
        return preMileageReqService.savePreMileageReq(mileageReqDto);
    }

    @DeleteMapping
    public int deletePreMileageReq(@RequestBody MileageReqDto mileageReqDto) {
        return preMileageReqService.deletePreMileageReq(mileageReqDto);
    }
}
