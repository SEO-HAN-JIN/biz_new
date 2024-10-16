package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CodeDto;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.service.system.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/codes")
@RequiredArgsConstructor
public class CodeRestController {

    private final CodeService codeService;

    @GetMapping("/master")
    public List<CamelCaseMap> findCdpatt(CodeDto codeDto) {
        return codeService.findCdpatt(codeDto);
    }

    @PostMapping("/master")
    public int saveMaster(@RequestBody List<CodeDto> codeDtoList) {
        return codeService.saveMaster(codeDtoList);
    }

    @DeleteMapping("/master")
    public int deleteMaster(@RequestBody List<CodeDto> codeDtoList) {
        return codeService.deleteMaster(codeDtoList);
    }

    @GetMapping("/detail")
    public List<CamelCaseMap> findDetailList(CodeDto codeDto) {
        return codeService.findDetailList(codeDto);
    }

    @PostMapping("/detail")
    public int saveDetail(@RequestBody List<CodeDto> codeDtoList) {
        return codeService.saveDetail(codeDtoList);
    }

    @DeleteMapping("/detail")
    public int deleteDetail(@RequestBody List<CodeDto> codeDtoList) {
        return codeService.deleteDetail(codeDtoList);
    }
}
